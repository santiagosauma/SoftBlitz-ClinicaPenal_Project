package com.leotesta017.clinicapenal.viewmodel.viewmodelUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.modelUsuario.Comentario
import com.leotesta017.clinicapenal.model.modelUsuario.Usuario
import com.leotesta017.clinicapenal.repository.userRepository.ComentarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ComentarioViewModel : ViewModel() {

    private val repository = ComentarioRepository()

    private val _comentario = MutableStateFlow<Comentario?>(null)
    val comentario: StateFlow<Comentario?> = _comentario

    private val _usuarioByComentario = MutableStateFlow<Map<String, String>>(emptyMap())
    val usuarioByComentario: StateFlow<Map<String, String>> = _usuarioByComentario

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Obtener un comentario por su ID
    fun fetchComentario(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getComentarioById(id)
                if (result != null) {
                    _comentario.value = result
                } else {
                    _error.value = "Comentario no encontrado"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el comentario: ${e.message}"
            }
        }
    }

    // Método para agregar un nuevo comentario a un caso
    fun addNewComentarioToCase(contenido: String, important: Boolean, userId: String, caseId: String) {
        viewModelScope.launch {
            val success = repository.addNewComentarioToCase(contenido, important, userId, caseId)
            if (!success) {
                _error.value = "Error al agregar el comentario al caso"
            }
        }
    }

    // Actualizar un comentario existente
    fun updateComentario(id: String, comentarioData: Map<String, Any>) {
        viewModelScope.launch {
            val success = repository.updateComentario(id, comentarioData)
            if (!success) {
                _error.value = "Error al actualizar el comentario"
            }
        }
    }

    fun getUserNameByComentarioId(id: String) {
        viewModelScope.launch {
            try {
                val result = repository.getUserNameByComentarioId(id)
                if (result?.isNotEmpty() == true) {
                    // Actualizar el mapa sin perder los otros valores ya cargados
                    _usuarioByComentario.update { currentMap ->
                        currentMap + (id to result)  // Añadir o actualizar el nombre del comentario
                    }
                } else {
                    _error.value = "Nombre de usuario no encontrado"
                }
            } catch (e: Exception) {
                _error.value = "Error al cargar el nombre: ${e.message}"
            }
        }
    }


    fun deleteComentario(caseId: String, comentarioId: String) {
        viewModelScope.launch {
            val success = repository.deleteComentarioFromCase(caseId, comentarioId)
            if (!success) {
                _error.value = "Error al actualizar el comentario"
            }
        }
    }
}
