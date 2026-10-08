package com.example.agridevelop

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChatScreen(
    currentLanguage: AppLanguage,
    onBack: () -> Unit,
    onNavigateToCropDoctor: () -> Unit
) {
    val context = LocalContext.current
    val darkForest = Color(0xFF0F3820)
    val brandForestGreen = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF4F7F4)
    val isTelugu = currentLanguage == AppLanguage.TELUGU

    var chatInput by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    var isThinking by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // -------------------------------------------------------------
    // Text-To-Speech (TTS) Engine
    // -------------------------------------------------------------
    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }
    DisposableEffect(currentLanguage) {
        var tts: TextToSpeech? = null
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val locale = if (isTelugu) Locale("te", "IN") else Locale("en", "IN")
                tts?.language = locale
            }
        }
        textToSpeech = tts

        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    fun speakOut(text: String) {
        textToSpeech?.stop()
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "agri_speech_id")
    }

    // -------------------------------------------------------------
    // Speech-To-Text (STT) Engine
    // -------------------------------------------------------------
    val speechRecognizer = remember {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            SpeechRecognizer.createSpeechRecognizer(context)
        } else null
    }

    val chatMessages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = "ai",
                text = if (isTelugu)
                    "నమస్కారం! నేను మీ అగ్రి AI సహాయకుడిని. మీ పంటల సమస్యలు, ఎరువులు లేదా పురుగుల నివారణ గురించి మైక్ ద్వారా మాట్లాడండి లేదా టైప్ చేయండి."
                else
                    "Namaste! I am your Agri AI Assistant. Tap the microphone to ask questions about crop diseases, fertilizers, or mandi advice."
            )
        )
    }

    // -------------------------------------------------------------
    // Send to Live Gemini AI
    // -------------------------------------------------------------
    fun handleSend(query: String) {
        if (query.isBlank() || isThinking) return

        chatMessages.add(ChatMessage("user", query))
        chatInput = ""
        keyboardController?.hide()

        val placeholderMessage = if (isTelugu) "సమాధానం విశ్లేషిస్తోంది..." else "Analyzing your question..."
        chatMessages.add(ChatMessage("ai", placeholderMessage))
        val currentAiIndex = chatMessages.size - 1
        isThinking = true

        coroutineScope.launch {
            listState.animateScrollToItem(chatMessages.size - 1)

            // Direct Call to Gemini Flash
            val realReply = AgriAiClient.getResponse(query)
            chatMessages[currentAiIndex] = ChatMessage("ai", realReply)
            isThinking = false

           // speakOut(realReply)
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    fun startListening() {
        if (speechRecognizer == null) {
            Toast.makeText(context, "Voice input not supported on this device", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, if (isTelugu) "te-IN" else "en-IN")
            putExtra(RecognizerIntent.EXTRA_PROMPT, if (isTelugu) "ఇప్పుడు మాట్లాడండి..." else "Speak your question now...")
        }

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) { isListening = true }
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() { isListening = false }
            override fun onError(error: Int) { isListening = false }
            override fun onResults(results: Bundle?) {
                isListening = false
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    val spokenText = matches[0]
                    handleSend(spokenText)
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer.startListening(intent)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startListening()
        } else {
            Toast.makeText(context, "Microphone permission required for voice chat", Toast.LENGTH_SHORT).show()
        }
    }

    val micButtonColor by animateColorAsState(
        targetValue = if (isListening) Color(0xFFDC2626) else brandForestGreen,
        label = "micButtonColor"
    )

    val quickQuestions = if (isTelugu) {
        listOf(
            "పత్తిలో గులాబీ రంగు పురుగు నివారణ?",
            "వరిలో ఎకరాకు యూరియా ఎంత వేయాలి?",
            "ఎర్ర చలక నేలలో ఏ పంటలు అనుకూలం?",
            "మిరపలో ఆకుముడుత నివారణ మందులు?"
        )
    } else {
        listOf(
            "How to treat Pink Bollworm in Cotton?",
            "How much Urea for 1 acre Paddy?",
            "Best high-yield crops for Red Soil?",
            "Remedy for leaf curl in Chilli?"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.SmartToy, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isTelugu) "అగ్రి AI వాయిస్ అసిస్టెంట్" else "Agri AI Voice Assistant",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                            Text(
                                text = when {
                                    isListening -> if (isTelugu) "వింటున్నాను..." else "Listening..."
                                    isThinking -> if (isTelugu) "సమాధానం సిద్ధం చేస్తోంది..." else "Gemini is thinking..."
                                    else -> if (isTelugu) "Gemini Flash ద్వారా లైవ్" else "Powered by Gemini Flash"
                                },
                                color = if (isListening) Color(0xFFFCA5A5) else Color(0xFFA7F3D0),
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToCropDoctor) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Crop Doctor", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkForest)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(pageBg)
                .padding(padding)
        ) {
            // Chat Messages Scroll List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(chatMessages) { msg ->
                    val isAi = msg.sender == "ai"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End
                    ) {
                        Surface(
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = if (isAi) 2.dp else 16.dp,
                                bottomEnd = if (isAi) 16.dp else 2.dp
                            ),
                            color = if (isAi) Color.White else brandForestGreen,
                            shadowElevation = 2.dp,
                            modifier = Modifier.widthIn(max = 310.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = msg.text,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp,
                                    color = if (isAi) Color(0xFF1E293B) else Color.White
                                )

                                if (isAi && !isThinking) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { speakOut(msg.text) }
                                            .padding(vertical = 2.dp, horizontal = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.VolumeUp,
                                            contentDescription = "Read Aloud",
                                            tint = brandForestGreen,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isTelugu) "వినండి" else "Listen",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = brandForestGreen
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Quick Prompt Suggestion Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(quickQuestions) { question ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        shadowElevation = 1.dp,
                        modifier = Modifier.clickable { handleSend(question) }
                    ) {
                        Text(
                            text = question,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF334155),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            // Bottom Input Bar (Voice Mic + Text + Send)
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = {
                            if (isListening) {
                                speechRecognizer?.stopListening()
                                isListening = false
                            } else {
                                if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                                    startListening()
                                } else {
                                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                }
                            }
                        },
                        containerColor = micButtonColor,
                        contentColor = Color.White,
                        modifier = Modifier.size(46.dp),
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = "Voice Input",
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = chatInput,
                        onValueChange = { chatInput = it },
                        placeholder = {
                            Text(
                                text = if (isListening) "వింటున్నాను..." else if (isTelugu) "మాట్లాడండి లేదా రాయండి..." else "Speak or type...",
                                fontSize = 13.sp,
                                color = Color(0xFF94A3B8)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(26.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = { handleSend(chatInput) }),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = brandForestGreen,
                            unfocusedBorderColor = Color(0xFFCBD5E1)
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FloatingActionButton(
                        onClick = { handleSend(chatInput) },
                        containerColor = brandForestGreen,
                        contentColor = Color.White,
                        modifier = Modifier.size(46.dp),
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}