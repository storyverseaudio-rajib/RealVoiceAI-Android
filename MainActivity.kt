package com.realvoiceai.app

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RealVoiceApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealVoiceApp() {
    var audioUri by remember { mutableStateOf<Uri?>(null) }
    var audioName by remember { mutableStateOf("No audio selected") }
    var voice by remember { mutableStateOf("Female") }
    var emotion by remember { mutableStateOf("Preserve original") }
    var processing by remember { mutableStateOf("Offline") }
    var status by remember { mutableStateOf("Ready to import audio") }

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            audioUri = uri
            audioName = uri.lastPathSegment ?: "Selected audio"
            status = "Audio selected. Conversion engine will be connected in the next stage."
        }
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF75E0B2),
            secondary = androidx.compose.ui.graphics.Color(0xFFB8A1FF),
            background = androidx.compose.ui.graphics.Color(0xFF10151B),
            surface = androidx.compose.ui.graphics.Color(0xFF1A222B)
        )
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = { Text("RealVoice AI") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Human-like AI Voice Studio", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Recorded audio → new voice → expressive output",
                    color = MaterialTheme.colorScheme.secondary
                )

                Card {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("1. Import recorded audio", style = MaterialTheme.typography.titleMedium)
                        Text(audioName, style = MaterialTheme.typography.bodyMedium)
                        Button(
                            onClick = { picker.launch(arrayOf("audio/*")) },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Choose Audio File") }
                        Text("Planned formats: MP3, WAV, M4A, AAC, FLAC", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Card {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("2. Target voice", style = MaterialTheme.typography.titleMedium)
                        val voices = listOf("Male", "Female", "Boy", "Girl", "Old Man", "Old Woman", "Monster", "Robot")
                        voices.chunked(2).forEach { pair ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                pair.forEach { item ->
                                    FilterChip(
                                        selected = voice == item,
                                        onClick = { voice = item },
                                        label = { Text(item) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (pair.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }

                Card {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("3. Emotion control", style = MaterialTheme.typography.titleMedium)
                        val emotions = listOf("Preserve original", "Happy", "Sad", "Angry", "Fear", "Romantic", "Confident", "Crying", "Laughing", "Sleepy")
                        emotions.chunked(2).forEach { pair ->
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                pair.forEach { item ->
                                    FilterChip(
                                        selected = emotion == item,
                                        onClick = { emotion = item },
                                        label = { Text(item) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (pair.size == 1) Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }

                Card {
                    Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("4. Processing mode", style = MaterialTheme.typography.titleMedium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            listOf("Offline", "Online").forEach { option ->
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(selected = processing == option, onClick = { processing = option })
                                    Text(option)
                                }
                            }
                        }
                        Text("Long audio target: 10–30 minutes (chunk processing planned)", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Button(
                    onClick = {
                        status = if (audioUri == null) {
                            "Please choose an audio file first."
                        } else {
                            "Prototype only: voice conversion engine is not connected yet."
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Convert Voice") }

                AssistChip(
                    onClick = {},
                    label = { Text("Status: $status") }
                )

                Text(
                    "Build 0.1 • UI prototype • AI conversion is not active yet",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
