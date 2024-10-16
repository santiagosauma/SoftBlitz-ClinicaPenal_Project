package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Appointment
import com.leotesta017.clinicapenal.model.modelUsuario.Case
import com.leotesta017.clinicapenal.model.modelUsuario.Comentario
import com.leotesta017.clinicapenal.model.modelUsuario.ExtraInfo
import com.leotesta017.clinicapenal.repository.userRepository.AppointmentRepository
import com.leotesta017.clinicapenal.repository.userRepository.CaseRepository
import com.leotesta017.clinicapenal.repository.userRepository.ExtraInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Comment
class CaseViewModel : ViewModel() {

    val repository = CaseRepository()
    val appointmentRepository = AppointmentRepository()
    val extrainfoRepository = ExtraInfoRepository()

    private val _case = MutableStateFlow<Case?>(null)
    val case: StateFlow<Case?> = _case

    // Estado para almacenar la última cita obtenida
    private val _lastAppointment = MutableStateFlow<Appointment?>(null)
    val lastAppointment: StateFlow<Appointment?> = _lastAppointment

    //Estado para almacenar el ultimo formulario del caso
    private val _lastExtraInfo = MutableStateFlow<ExtraInfo?>(null)
    val lastExtraInfo: StateFlow<ExtraInfo?> = _lastExtraInfo

    private val _unrepresentedCasesWithLastAppointment = MutableStateFlow<List<Triple<Case, String, Boolean>>>(emptyList())
    val unrepresentedCasesWithLastAppointment: StateFlow<List<Triple<Case, String, Boolean>>> = _unrepresentedCasesWithLastAppointment

    private val _caseWithDetails = MutableStateFlow<Pair<Case, Triple<List<Appointment>, List<Comentario>, List<ExtraInfo>>>?>(null)
    val caseWithDetails: StateFlow<Pair<Case, Triple<List<Appointment>, List<Comentario>, List<ExtraInfo>>>?> = _caseWithDetails

    private val _userGeneralId = MutableStateFlow<String?>(null)
    val userGeneralId: StateFlow<String?> = _userGeneralId

    private val _caseDeleted = MutableStateFlow<Boolean>(false)
    val caseDeleted: StateFlow<Boolean> = _caseDeleted

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Obtener un caso por su ID
    fun fetchCase(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getCaseById(id)
                if (result != null) {
                    _case.value = result
                } else {
                    _error.value = "Caso no encontrado"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el caso: ${e.message}"
            }
        }
    }

    fun fetchUnrepresentedCasesWithLastAppointment() {
        viewModelScope.launch {
            try {
                val cases = repository.getAllUnrepresentedCasesWithLastAppointment(appointmentRepository)
                _unrepresentedCasesWithLastAppointment.value = cases
            } catch (e: Exception) {
                _error.value = "Error al cargar los casos sin representación: ${e.message}"
            }
        }
    }

    // Agregar un nuevo caso
    fun addNewCase(case: Case, userId: String) {
        viewModelScope.launch {
            val success = repository.addCase(case, userId)
            if (!success) {
                _error.value = "Error al agregar el caso"
            }
        }
    }

    // Asignar abogado o estudiante al caso
    fun assignUserToCase(caseId: String, userId: String, role: String) {
        viewModelScope.launch {
            val success = repository.assignUserToCase(caseId, userId, role)
            if (!success) {
                _error.value = "Error al asignar $role: Verifique el tipo de usuario o el ID"
            }
        }
    }

    // Actualizar un caso existente
    fun updateCase(id: String, caseData: Map<String, Any>) {
        viewModelScope.launch {
            val success = repository.updateCase(id, caseData)
            if (!success) {
                _error.value = "Error al actualizar el caso"
            }
        }
    }

    // Función para eliminar un caso y sus elementos asociados
    fun updateExtraInfoOfCase(caseId: String, data: Map<String,Any>) {
        viewModelScope.launch {
            val success = repository.updateLastExtraInfoInCase(caseId = caseId, extraInfoData = data)
            if (!success) {
                _error.value = "Error al eliminar el caso o elementos asociados."  // Indicar que el caso ha sido eliminado con éxito
            }
        }
    }


    // Función para eliminar un caso y sus elementos asociados
    fun discardCase(caseId: String) {
        viewModelScope.launch {
            val success = repository.updateCaseToDiscard(caseId)
            if (!success) {
                _error.value = "Error al eliminar el caso o elementos asociados."  // Indicar que el caso ha sido eliminado con éxito
            }
        }
    }

    // Función para eliminar un caso y sus elementos asociados
    fun deleteCase(caseId: String) {
        viewModelScope.launch {
            val success = repository.deleteCase(caseId)
            if (!success) {
                _error.value = "Error al eliminar el caso o elementos asociados."  // Indicar que el caso ha sido eliminado con éxito
            }
        }
    }

    fun fetchCaseWithDetails(caseId: String) {
        viewModelScope.launch {
            try {
                val result = repository.getCaseWithDetails(caseId)
                if (result != null) {
                    _caseWithDetails.value = result
                } else {
                    _error.value = "El caso no se encontró o no tiene detalles asociados"
                }
            } catch (e: Exception) {
                _error.value = "Error al obtener el caso: ${e.message}"
            }
        }
    }

    fun fetchLastAppointment(caseId: String) {
        viewModelScope.launch {
            try {
                // Resetear el estado antes de la nueva llamada
                resetLastAppointment()

                // Llamar al repositorio para obtener el último appointment
                val lastAppointment = repository.getLastAppointmentForCase(caseId, appointmentRepository)

                if (lastAppointment != null) {
                    _lastAppointment.value = lastAppointment
                    _error.value = null // Sin error
                } else {
                    _lastAppointment.value = null
                    _error.value = "No se encontró ninguna cita para este caso."
                }
            } catch (e: Exception) {
                _error.value = "Error al obtener la última cita: ${e.message}"
            }
        }
    }

    // Método para resetear el estado de la última cita
    fun resetLastAppointment() {
        _lastAppointment.value = null
        _error.value = null
    }


    fun fetchLastExtraInfo(caseId: String) {
        viewModelScope.launch {
            try {
                // Resetear el estado antes de la nueva llamada
                resetLastExtraInfo()

                // Llamar al repositorio para obtener el último appointment
                val lastExtraInfo = repository.getLastExtraInfoForCase(caseId, extrainfoRepository)

                if (lastExtraInfo != null) {
                    _lastExtraInfo.value = lastExtraInfo
                    _error.value = null // Sin error
                } else {
                    _lastExtraInfo.value = null
                    _error.value = "No se encontró ninguna cita para este caso."
                }
            } catch (e: Exception) {
                _error.value = "Error al obtener la última cita: ${e.message}"
            }
        }
    }

    // Método para resetear el estado de la última cita
    fun resetLastExtraInfo() {
        _lastExtraInfo.value = null
        _error.value = null
    }

    // Función para actualizar la valoración del appointment y agregar comentario si existe
    fun updateAppointmentAndAddComment(
        caseId: String,
        rating: Int,
        commentContent: String,
        userId: String
    ) {
        viewModelScope.launch {
            val success = repository.updateAppointmentAndAddCommentIfExists(
                            caseId = caseId,
                            rating = rating,
                            commentContent = commentContent,
                            userId = userId)
            if (!success) {
                _error.value = "Error al actualizar el caso o elementos asociados."
            }
        }

    }

    fun getUserGenalIdFromCaseId(caseId: String)
    {
        viewModelScope.launch {
            try {
                val userId = repository.findUserByCaseId(caseId)

                if (userId != null) {
                    _userGeneralId.value = userId
                    _error.value = null // Sin error
                } else {
                    _userGeneralId.value = null
                    _error.value = "No se encontró ninguna cita para este caso."
                }
            } catch (e: Exception) {
                _error.value = "Error al obtener la última cita: ${e.message}"
            }
        }
    }
}

