package com.leotesta017.clinicapenal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leotesta017.clinicapenal.model.Categoria
import com.leotesta017.clinicapenal.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val repository = CategoryRepository()

    // Estado que contiene la lista de categorías básicas
    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias: StateFlow<List<Categoria>> = _categorias

    // Estado para manejar posibles errores
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Estado que contiene el contenido de un categoria
    private val _contenido = MutableStateFlow<String>("")
    val contenido: StateFlow<String> = _contenido

    // Método para obtener las categorías básicas
    init {
        fetchCategorias()
    }

    private fun fetchCategorias() {
        viewModelScope.launch {
            try {
                val categoriasList = repository.getCategorias()
                _categorias.value = categoriasList
            } catch (e: Exception) {
                _error.value = "Error al cargar las categorías"
            }
        }
    }

    // Método para obtener el contenido de un servicio por su ID
    fun fetchContenidoById(categoriaId: String) {
        viewModelScope.launch {
            try {
                val contenido = repository.getContenidoById(categoriaId)
                _contenido.value = contenido
            } catch (e: Exception) {
                _error.value = "Error al cargar el contenido"
            }
        }
    }
}
