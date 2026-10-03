package com.example.agridevelop

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
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChatScreen(
    currentLanguage: AppLanguage,
    onBack: () -> Unit,
    onNavigateToCropDoctor: () -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val brandForestGreen = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF4F7F4)
    val isTelugu = currentLanguage == AppLanguage.TELUGU

    var chatInput by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val chatMessages = remember {
        mutableStateListOf(
            ChatMessage(
                sender = "ai",
                text = if (isTelugu)
                    "నమస్కారం! నేను మీ అగ్రి AI సహాయకుడిని. పంటలు, తెగుళ్లు, ఎరువులు మరియు నేల సంబంధిత ఏ ప్రశ్నలైనా అడగవచ్చు."
                else
                    "Namaste! I am your Agri AI Assistant. Ask me anything regarding crops, plant diseases, fertilizers, and soil care."
            )
        )
    }

    val quickQuestions = if (isTelugu) {
        listOf(
            "పత్తిలో గులాబీ రంగు పురుగు నివారణ?",
            "వరిలో అగ్గి తెగులు ఎరువులు?",
            "నల్లరేగడి నేలలో ఏ పంట వేయాలి?",
            "యూరియా ఎంత మోతాదులో వాడాలి?"
        )
    } else {
        listOf(
            "Best fertilizer for Cotton?",
            "How to treat Paddy blast?",
            "Ideal crops for Black Soil?",
            "Organic pest control tips"
        )
    }

    fun handleSend(query: String) {
        if (query.isBlank()) return
        chatMessages.add(ChatMessage("user", query))
        chatInput = ""
        keyboardController?.hide()

        val reply = when {
            query.contains("cotton", ignoreCase = true) || query.contains("పత్తి", ignoreCase = true) ->
                if (isTelugu) "పత్తిలో గులాబీ రంగు పురుగు నివారణకు ప్రొఫెనోఫాస్ 2ml/లీటరు లేదా నిమ్ ఆయిల్ 5ml పిచికారీ చేయండి."
                else "For Cotton bollworm control, spray Profenofos @ 2ml/L or 1500ppm Neem Oil. Check pheromone traps regularly."

            query.contains("paddy", ignoreCase = true) || query.contains("వరి", ignoreCase = true) ->
                if (isTelugu) "వరిలో అగ్గి తెగులు నివారణకు ట్రైసైక్లాజోల్ 0.6 గ్రాము లేదా కాసుగామైసిన్ 2.5 ml ప్రతి లీటరు నీటికి కలపండి."
                else "For Paddy Blast, apply Tricyclazole 75% WP @ 0.6g/L of water. Maintain intermittent flooding."

            query.contains("soil", ignoreCase = true) || query.contains("నేల", ignoreCase = true) ->
                if (isTelugu) "తెలంగాణ ప్రాంతంలో నల్లరేగడి నేలలో పత్తి, సోయాబీన్, అలాగే ఎర్ర నేలల్లో వేరుశనగ, కంది అత్యుత్తమ దిగుబడిని ఇస్తాయి."
                else "Black soils are ideal for Cotton/Soybean, while Red sandy loams provide high yields for Groundnut & Red Gram."

            query.contains("urea", ignoreCase = true) || query.contains("ఎరువు", ignoreCase = true) ->
                if (isTelugu) "యూరియాను ఒకేసారి వేయకుండా 3 సమాన భాగాలుగా విభజించి, తగిన తేమ ఉన్నప్పుడే వేయండి."
                else "Split Urea into 3 split doses (basal, vegetative, flowering) rather than a single application to reduce nitrogen leaching."

            else ->
                if (isTelugu) "మీ ప్రశ్నకు సమాచారం పరిశీలించబడుతోంది. ఖచ్చితమైన విశ్లేషణ కోసం 'క్రాప్ డాక్టర్' కెమెరా ద్వారా ఆకు ఫోటో తీసి స్కాన్ చేయండి."
                else "For an immediate visual health check of your crop leaves, consider using our Crop Doctor camera scanner."
        }
        chatMessages.add(ChatMessage("ai", reply))
        coroutineScope.launch {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
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
                                text = if (isTelugu) "అగ్రి AI సహాయకుడు" else "Agri AI Assistant",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 17.sp
                            )
                            Text(
                                text = if (isTelugu) "ఆన్‌లైన్ • 24x7 రైతు సలహాదారు" else "Online • 24x7 Farmer Advisor",
                                color = Color(0xFFA7F3D0),
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
            // Chat conversation messages
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
                            modifier = Modifier.widthIn(max = 290.dp)
                        ) {
                            Text(
                                text = msg.text,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = if (isAi) Color(0xFF1E293B) else Color.White,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }
            }

            // Quick Query Chips
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

            // Bottom Input Bar
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
                    OutlinedTextField(
                        value = chatInput,
                        onValueChange = { chatInput = it },
                        placeholder = {
                            Text(
                                text = if (isTelugu) "ఏదైనా అడగండి..." else "Ask crop, soil or pest questions...",
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
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}