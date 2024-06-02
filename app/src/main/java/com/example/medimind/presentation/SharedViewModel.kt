package com.example.medimind.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medimind.data.model.Event
import com.example.medimind.data.model.User
import com.example.medimind.domain.events.AddNewReminderUIEvent
import com.example.medimind.domain.events.EventUIEvent
import com.example.medimind.domain.events.HomeUIEvent
import com.example.medimind.domain.events.MedicationUIEvent
import com.example.medimind.domain.repository.AuthRepository
import com.example.medimind.domain.repository.UserPref
import com.example.medimind.domain.repository.UserRepository
import com.example.medimind.domain.rules.Validator
import com.example.medimind.domain.states.AddNewReminderUIState
import com.example.medimind.util.Constants.ALL_EVENTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val userPref: UserPref,
    private val authRepo: AuthRepository
) : ViewModel() {

    val user = userPref.getUserModel().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        User()
    )

    private val _addNewReminderUIState = MutableStateFlow(AddNewReminderUIState())
    val addNewReminderUIState = _addNewReminderUIState.asStateFlow()


    private val _medicationItems = MutableStateFlow<List<Event>>(emptyList())
    val medicationItems = _medicationItems.asStateFlow()


    private val _eventItems = MutableStateFlow<List<Event>>(emptyList())
    val eventItems = _eventItems.asStateFlow()

    private val _eventFrom = MutableStateFlow(0) // 0 -> Home 1 -> MEDICATION, 2 -> EVENT
    val eventFrom = _eventFrom.asStateFlow()

    private val _navigationDrawerState = MutableStateFlow(false)
    val navigationDrawerState = _navigationDrawerState.asStateFlow()

    private val _navigateBackState = MutableStateFlow(false)
    val navigateBackState = _navigateBackState.asStateFlow()

    fun onHomeUIEvent(event: HomeUIEvent) {
        when (event) {
            HomeUIEvent.AddNewButtonClicked -> {
                _eventFrom.update { 0 }
            }

            HomeUIEvent.MenuIconClicked -> {
                _navigationDrawerState.update { true }
            }
            HomeUIEvent.CloseStateClicked -> {
                _navigationDrawerState.update { false }
            }

            HomeUIEvent.SignOutButtonClicked -> {
                signOut()
            }
        }
    }

    fun onAddNewReminderUIEvent(event: AddNewReminderUIEvent) {
        when (event) {
            is AddNewReminderUIEvent.TypeChanged -> {
                _addNewReminderUIState.value = addNewReminderUIState.value.copy(type = event.type)
            }

            is AddNewReminderUIEvent.NameChanged -> {
                _addNewReminderUIState.value = addNewReminderUIState.value.copy(name = event.name)
            }

            is AddNewReminderUIEvent.DateChanged -> {
                _addNewReminderUIState.value = addNewReminderUIState.value.copy(date = event.date)
            }

            is AddNewReminderUIEvent.TimeChanged -> {
                _addNewReminderUIState.value = addNewReminderUIState.value.copy(time = event.time)
            }

            is AddNewReminderUIEvent.StockChanged -> {
                _addNewReminderUIState.value = addNewReminderUIState.value.copy(stock = event.stock)
            }

            AddNewReminderUIEvent.SaveButtonClicked -> {
                if (validateAllStates()) {
                    _navigateBackState.update { true }
                    setEvent()
                }
            }

            AddNewReminderUIEvent.GoBackButtonClicked -> {
                _addNewReminderUIState.value = AddNewReminderUIState()
            }
        }
    }

    fun onUpcomingEventsUIEvent(event: EventUIEvent) {
        when (event) {
            EventUIEvent.AddNewButtonClicked -> {
                _eventFrom.update { 2 }
            }
        }
    }

    fun onMedicationUIEvent(event: MedicationUIEvent) {
        when (event) {
            is MedicationUIEvent.DecreaseStockButtonClicked -> {
                updateMedicationStock(event.eventId, event.currentStock)
            }

            is MedicationUIEvent.RestockButtonClicked -> {
                updateMedicationStock(event.eventId, event.currentStock)
            }

            MedicationUIEvent.AddNewButtonClicked -> {
                _eventFrom.update { 1 }
            }
        }
    }


    private fun setEvent() = viewModelScope.launch {
        val event = Event(
            type = if (eventFrom.value == 2) {
                ALL_EVENTS[2]
            } else {
                addNewReminderUIState.value.type
            },
            name = addNewReminderUIState.value.name,
            date = addNewReminderUIState.value.date,
            time = addNewReminderUIState.value.time,
            stock = addNewReminderUIState.value.stock.toInt(),
        )
        userRepo.createEvent(event, user.value.email).collectLatest {
            _addNewReminderUIState.value = AddNewReminderUIState()
        }
    }

    fun getMedications() = viewModelScope.launch {
        userRepo.getMedication(user.value.email).collectLatest {
            _medicationItems.value = it
        }
    }

    fun getEvents() = viewModelScope.launch {
        userRepo.getEvent(user.value.email).collectLatest {
            _eventItems.value = it
        }
    }


    private fun updateMedicationStock(eventId: String, currentStock: Int) =
        viewModelScope.launch {
            userRepo.updateMedicationStock(eventId, user.value.email, currentStock).collectLatest {

            }
        }


    private fun signOut() = viewModelScope.launch {
        authRepo.signOut().collectLatest {
            if (it.data == true) {
                userPref.saveUserModel(User())
                _navigationDrawerState.update { false }
            }
        }
    }


    private fun validateAllStates(): Boolean {
        val nameResult = Validator.validateName(addNewReminderUIState.value.name)
        val dateResult = Validator.validateDate(addNewReminderUIState.value.date)
        val timeResult = Validator.validateTime(addNewReminderUIState.value.time)
        val stockResult = Validator.validateStock(addNewReminderUIState.value.stock)

        _addNewReminderUIState.value = addNewReminderUIState.value.copy(nameError = nameResult)
        _addNewReminderUIState.value = addNewReminderUIState.value.copy(dateError = dateResult)
        _addNewReminderUIState.value = addNewReminderUIState.value.copy(timeError = timeResult)
        _addNewReminderUIState.value = addNewReminderUIState.value.copy(stockError = stockResult)

        return nameResult == null
                && dateResult == null
                && timeResult == null
                && stockResult == null

    }
}