package org.example.rickandmortyapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.rickandmortyapp.model.RickCharacter
import org.example.rickandmortyapp.network.getCharacterResponse
@Composable
fun CharacterScreen() {
    var characters by remember { mutableStateOf<List<RickCharacter>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var currentPage by remember { mutableStateOf(1) }
    var maxPage by remember { mutableStateOf(1) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentPage) {
        isLoading = true
        errorMessage = null
        try {
            val response = getCharacterResponse(currentPage)
            characters = response.results
            maxPage = response.info.pages
        } catch (e: Exception) {
            errorMessage = "Error al cargar los personajes"
        }
        isLoading = false
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            Loading()
        } else if (errorMessage != null) {
            Text(errorMessage ?: "", color = Color.Red)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f) // IMPORTANTE para que use el espacio disponible y no empuje los botones fuera
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
            ) {
                items(characters) { character ->
                    CharacterCard(character)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Button(
                    onClick = { if (currentPage > 1) currentPage-- },
                    enabled = currentPage > 1
                ) {
                    Text("Anterior")
                }

                Text(
                    "Página $currentPage de $maxPage",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Button(
                    onClick = { if (currentPage < maxPage) currentPage++ },
                    enabled = currentPage < maxPage
                ) {
                    Text("Siguiente")
                }
            }
        }
    }
}


@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFF00695C)) // Indicador con color personalizado
    }
}
