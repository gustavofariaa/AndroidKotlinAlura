package br.com.alura.technews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class FormularioNoticiaViewModel(private val repository: NoticiaRepository) : ViewModel() {

    fun buscaPorId(noticiaId: Long): LiveData<Noticia?> = repository.buscaPorId(noticiaId)

    fun salva(noticia: Noticia): LiveData<Resource<Void?>> =
        if (noticia.id > 0) repository.edita(noticia) else repository.salva(noticia)
}