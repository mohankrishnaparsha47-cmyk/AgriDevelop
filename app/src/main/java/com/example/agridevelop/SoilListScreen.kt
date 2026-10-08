package com.example.agridevelop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

data class SoilTypeData(
    val id: String,
    val name: String,
    val localName: String,
    val phRange: String,
    val texture: String,
    val waterHolding: String,
    val drainage: String,
    val organicCarbon: String,
    val majorNutrients: String,
    val topCrops: List<String>,
    val secondaryCrops: List<String>,
    val basalFertilizer: String,
    val topDressing: String,
    val micronutrientCare: String,
    val irrigationGuide: String,
    val commonProblemsAndFix: String,
    val themeColor: Color,
    val localImageRes: Int? = null,
    val webImageUrl: String? = null
)

object SoilCatalog {
    val allSoils = listOf(
        SoilTypeData(
            id = "black_soil",
            name = "Black Soil (Regur / Clay)",
            localName = "నల్లరేగడి నేలలు",
            phRange = "7.2 - 8.5 (Slightly Alkaline)",
            texture = "Heavy clay (montmorillonite group), deep fissures in summer, sticky when wet",
            waterHolding = "Very High (Retains 60-70% moisture; holds 8-10 days)",
            drainage = "Slow to poor internal drainage; prone to waterlogging in heavy downpours",
            organicCarbon = "Low to medium (0.4% - 0.6%)",
            majorNutrients = "Naturally high in Calcium, Magnesium, Potassium, Carbonates; low in Nitrogen and Available Phosphorus",
            topCrops = listOf(
                "Cotton / పత్తి (Bt Hybrid varieties)",
                "Soybean / సోయాబీన్ (JS 335, JS 9305)",
                "Chilli / మిరప (Teja, G4 varieties)",
                "Bengal Gram / శనగ (JG 11, KAK 2)",
                "Paddy / వరి (BPT 5204, MTU 1010 in lowlands)",
                "Sunflower / పొద్దుతిరుగుడు"
            ),
            secondaryCrops = listOf("Jowar / జొన్న", "Pigeonpea / కంది", "Safflower / కుసుమ", "Maize / మొక్కజొన్న"),
            basalFertilizer = "Apply 40-50 kg DAP + 25-30 kg MOP (Muriate of Potash) per acre at final ploughing. Add 4-5 carts well-decomposed FYM.",
            topDressing = "Split Nitrogen application: 25 kg Urea at 30-35 DAS (Days After Sowing) and 25 kg Urea at flowering/boll/pod formation during good soil moisture.",
            micronutrientCare = "Black soils show high Zinc and Iron chlorosis. Soil apply 10-15 kg Zinc Sulphate (21%) per acre once every 2 years. Foliar spray 0.5% FeSO4 for leaf yellowing.",
            irrigationGuide = "Use broad bed & furrow (BBF) or ridge-furrow layout. Avoid over-irrigation; irrigate at 10-14 day intervals depending on season.",
            commonProblemsAndFix = "Problem: Cracking causes root shearing; waterlogging causes root rot. Solution: Avoid severe dry spells, practice mulching, and provide surface drainage channels.",
            themeColor = Color(0xFF37474F),
            localImageRes = R.drawable.black_soil
        ),
        SoilTypeData(
            id = "red_soil",
            name = "Red Sandy Soil (Chalaka)",
            localName = "ఎర్ర నేలలు / చలక నేలలు",
            phRange = "6.0 - 7.2 (Neutral to Slightly Acidic)",
            texture = "Coarse sandy loam to porous gravelly loam with iron oxide enrichment",
            waterHolding = "Moderate to Low (Quick draining; holds 3-5 days)",
            drainage = "Rapid to excessive; excellent root aeration",
            organicCarbon = "Very Low (0.2% - 0.4%)",
            majorNutrients = "Deficient in Nitrogen, Phosphorus, Humus; moderate in Potassium and high in free Iron",
            topCrops = listOf(
                "Groundnut / వేరుశనగ (K6, Kadiri Lepakshi)",
                "Maize / మొక్కజొన్న (DHM 117, Pioneer hybrids)",
                "Red Gram / కంది (PRG 176, Asha, WRG 65)",
                "Castor / ఆముదం (DCH 519, PCH 111)",
                "Millets / కొర్రలు, రాగులు",
                "Tomato & Onion / టమోటా, ఉల్లి"
            ),
            secondaryCrops = listOf("Sesame / నువ్వులు", "Cowpea / అలసందలు", "Watermelon / పుచ్చ", "Guava / జామ"),
            basalFertilizer = "Apply 50 kg SSP (Single Super Phosphate) + 20 kg MOP per acre. Add 5 tonnes compost or sheep manure per acre to build organic carbon.",
            topDressing = "Top dress Urea in 3 split doses: 20 kg at sowing/transplanting, 20 kg at 25 DAS, and 15 kg at 45 DAS to prevent nitrogen leaching.",
            micronutrientCare = "Apply Gypsum @ 200 kg/acre at 40-45 DAS for Groundnut (crucial for Calcium/Sulphur and pod filling). Spray 0.2% Borax at flowering.",
            irrigationGuide = "Needs light and frequent waterings (every 4-6 days) or drip fertigation. Drip reduces nutrient leaching by over 40%.",
            commonProblemsAndFix = "Problem: Rapid nutrient runoff and surface crusting. Solution: Incorporate tank silt (చెరువు మట్టి) @ 10-15 tractor loads/acre and cover crops.",
            themeColor = Color(0xFFD84315),
            localImageRes = R.drawable.red_soil
        ),
        SoilTypeData(
            id = "alluvial_soil",
            name = "Alluvial & Loamy Soil",
            localName = "ఒండ్రు నేలలు / మెత్తని మట్టి",
            phRange = "6.5 - 7.5 (Optimal Neutral)",
            texture = "Deep, balanced silt-clay-fine sand mixture deposited by river runoff",
            waterHolding = "High & Well-aerated (Optimum available water capacity)",
            drainage = "Moderate to good drainage with high capillary water movement",
            organicCarbon = "Medium to high (0.6% - 0.85%)",
            majorNutrients = "High in Potash, Lime, and trace minerals; moderate in Phosphoric acid; responsive to Nitrogen",
            topCrops = listOf(
                "Paddy / వరి (MTU 1061, RNR 15048 / Telangana Sona)",
                "Sugarcane / చెరకు (Co 86032, Co 0238)",
                "Banana / అరటి (Grand Naine)",
                "Turmeric / పసుపు (Duggirala, Salem)",
                "Wheat / గోధుమ",
                "Leafy Vegetables & Gourds / కూరగాయలు"
            ),
            secondaryCrops = listOf("Papaya / బొప్పాయి", "Chilli / మిరప", "Mustard / ఆవాలు", "Maize / మొక్కజొన్న"),
            basalFertilizer = "Apply 50 kg DAP + 30 kg Potash (MOP) per acre at land preparation. Green manuring with Sunnhemp or Dhaincha prior to transplanting adds 25 kg natural N.",
            topDressing = "Apply Neem Coated Urea in 3 equal splits: 1/3 at basal, 1/3 at tillering (21 DAS), and 1/3 at panicle initiation (45 DAS).",
            micronutrientCare = "Zinc Sulphate 10 kg/acre every 3 seasons. Apply 5 kg Ferrous Sulphate if seedlings show pale leaf strips in nursery stages.",
            irrigationGuide = "Ideal for canal, flood, and sprinkler systems. Maintain 2-3 cm shallow standing water for paddy during vegetative phases.",
            commonProblemsAndFix = "Problem: Soil compaction from heavy machinery. Solution: Practice deep summer ploughing once in two years followed by rotavator leveling.",
            themeColor = Color(0xFF5D4037),
            webImageUrl = "https://images.unsplash.com/photo-1625246333195-78d9c38ad449?q=80&w=400&auto=format&fit=crop"
        ),
        SoilTypeData(
            id = "laterite_soil",
            name = "Laterite & Gravelly Soil",
            localName = "లేటరైట్ / ఎరుపు గరప నేలలు",
            phRange = "4.8 - 6.0 (Strongly to Moderately Acidic)",
            texture = "Coarse gravelly with hard vesicular nodules, rich in aluminum and iron hydrated oxides",
            waterHolding = "Low (Highly porous, water percolates rapidly)",
            drainage = "Excessive; high leaching loss of base cations (Ca, Mg, K)",
            organicCarbon = "Low (0.3% - 0.5%)",
            majorNutrients = "Deficient in Calcium, Magnesium, Potassium, Boron; Phosphorus is chemically fixed/unavailable",
            topCrops = listOf(
                "Cashew / జీడిమామిడి (BPP 8, VRI 3)",
                "Mango / మామిడి (Banganapalli, Suvarnarekha)",
                "Turmeric & Ginger / పసుపు, అల్లం",
                "Tapioca / కర్రపెండలం",
                "Coffee / కాఫీ",
                "Sweet Potato / చిలగడదుంప"
            ),
            secondaryCrops = listOf("Arecanut / పోకచెక్క", "Rubber / రబ్బర్", "Pineapple / అనాస", "Jackfruit / పనస"),
            basalFertilizer = "Apply Rock Phosphate (Mussoorie Rock Phosphate) @ 100 kg/acre instead of water-soluble SSP/DAP to avoid phosphorus fixation. Combine with 25 kg MOP.",
            topDressing = "Split application of Ammonium Sulphate or Calcium Ammonium Nitrate (CAN) instead of pure Urea to reduce acidity increases.",
            micronutrientCare = "Soil acidity reclamation: Broadcast Agricultural Lime (Chuna) or Dolomite @ 200-250 kg/acre once every 3 years based on soil test.",
            irrigationGuide = "Drip irrigation with fertigation rings around tree basins. Mulch tree basins with dry leaves to conserve moisture.",
            commonProblemsAndFix = "Problem: High acidity locks nutrients and hardens surface crusts. Solution: Regular liming and addition of vermicompost or pressmud cake.",
            themeColor = Color(0xFFBF360C),
            localImageRes = R.drawable.laterate_soil
        ),
        SoilTypeData(
            id = "saline_soil",
            name = "Saline & Alkaline Soil (Chowdu)",
            localName = "చౌడు నేలలు / క్షార నేలలు",
            phRange = "8.2 - 9.8 (High pH & High Electrical Conductivity)",
            texture = "Dense, hard pan structure, white/black salt incrustation, very poor aeration",
            waterHolding = "Variable; poor intake rate, surface ponding occurs easily",
            drainage = "Very Poor (Impermeable subsoil barrier)",
            organicCarbon = "Very Poor (< 0.25%)",
            majorNutrients = "Excessive Sodium and Chloride ions; severely deficient in available Nitrogen, Zinc, and Iron",
            topCrops = listOf(
                "Salt-Tolerant Paddy / చౌడు వరి (CSR 10, CSR 27, CSR 36)",
                "Dhaincha (Green Manure) / జీలుగ",
                "Cotton / పత్తి (Salt-tolerant varieties)",
                "Barley / బార్లీ",
                "Mustard / ఆవాలు",
                "Bermuda Grass & Fodder / గడ్డి జాతులు"
            ),
            secondaryCrops = listOf("Sugarbeet / బీట్‌రూట్", "Guar / గోరుచిక్కుడు", "Pomegranate / దానిమ్మ", "Spinach / పాలకూర"),
            basalFertilizer = "Reclamation mandatory: Apply Gypsum @ 1.5 to 2.5 tonnes/acre based on ESP test. Flood with clean water and drain out stagnant salt wash after 48 hours.",
            topDressing = "Use Ammonium Sulphate or Urea with sulphur. Avoid basic fertilizers like Calcium Nitrate. Apply 25% extra Nitrogen than regular recommended dose.",
            micronutrientCare = "Zinc Sulphate @ 20 kg/acre + Ferrous Sulphate @ 10 kg/acre. Foliar spray 1% Zinc + 1% Urea at 20 and 40 DAS.",
            irrigationGuide = "Maintain frequent shallow irrigations. Never allow soil to completely dry out, as capillary action pulls subsurface salts back to the root zone.",
            commonProblemsAndFix = "Problem: High salt toxicity stunts root emergence and burns leaf margins. Solution: Deep subsurface drainage pipes, green manuring with Dhaincha, and gypsum leaching.",
            themeColor = Color(0xFF6B7280),
            localImageRes = R.drawable.alkaline_soil
        ),
        SoilTypeData(
            id = "sandy_soil",
            name = "Sandy & Riverbed Soil",
            localName = "ఇసుక నేలలు / రేగడి ఇసుక",
            phRange = "6.5 - 7.5 (Neutral)",
            texture = "Loose, gritty particles with over 85% sand and minimal silt or clay",
            waterHolding = "Very Low (Water filters through in hours; hold time 1-2 days)",
            drainage = "Excessive, rapid percolation; near zero surface retention",
            organicCarbon = "Very Low (< 0.2%)",
            majorNutrients = "Extremely low in all macro and micronutrients; no cation exchange capacity",
            topCrops = listOf(
                "Watermelon / పుచ్చకాయ (Sugar Baby, Max)",
                "Muskmelon / కర్బూజ",
                "Cucumber & Bottle Gourd / దోస, సొరకాయ",
                "Groundnut / వేరుశనగ (Summer irrigated)",
                "Carrot & Radish / క్యారెట్, ముల్లంగి",
                "Sweet Corn / స్వీట్ కార్న్"
            ),
            secondaryCrops = listOf("Bitter Gourd / కాకర", "Potato / బంగాళాదుంప", "Ridge Gourd / బీర", "Asparagus"),
            basalFertilizer = "Heavy FYM or Poultry Manure @ 6-8 tonnes/acre is essential. Apply 30 kg SSP and 20 kg Potassium Sulphate at planting.",
            topDressing = "Never apply fertilizers in single bulky doses. Use drip fertigation with water-soluble 19:19:19 (NPK) @ 3-4 kg per acre every 4 days.",
            micronutrientCare = "Regular foliar nutrition is needed. Spray Formula-4 or Grade-II micronutrient mixture @ 2.5 ml/L at weekly intervals.",
            irrigationGuide = "Drip irrigation is mandatory. Run drip for 30-45 minutes daily or twice daily during peak summer fruit development.",
            commonProblemsAndFix = "Problem: Fertilizers and water wash straight into underground aquifers. Solution: Apply coir pith compost, tank silt, or bentonite clay to improve matrix texture.",
            themeColor = Color(0xFF8B5CF6),
            webImageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=400&auto=format&fit=crop"
        ),
        SoilTypeData(
            id = "peaty_soil",
            name = "Peaty & Marshy Soil",
            localName = "ఊబి నేలలు / బురద నేలలు",
            phRange = "3.8 - 5.4 (Strongly Acidic)",
            texture = "Spongy, dark fibrous organic matter decayed under anaerobic waterlogged conditions",
            waterHolding = "Extremely High (Permanently submerged or saturated)",
            drainage = "Impeded / Nil without artificial ditches",
            organicCarbon = "Excessive (10% - 40%)",
            majorNutrients = "High in organic Nitrogen; severely deficient in Potassium, Available Phosphorus, and Copper",
            topCrops = listOf(
                "Deepwater & Scented Paddy / ముంపు వరి",
                "Jute & Mesta / జనపనార, గోగు",
                "Colocasia & Taro / చామదుంప",
                "Water Spinach & Kangkong",
                "Water Chestnut / సింగార",
                "Lotus Root / తామర"
            ),
            secondaryCrops = listOf("Mint / పుదీనా", "Elephant Foot Yam / కంద", "Ginger / అల్లం (on raised ridges)", "Aquatic Greens"),
            basalFertilizer = "Apply Potash (MOP) heavily @ 40-50 kg/acre. Add Rock Phosphate @ 75 kg/acre. Limit Nitrogen fertilizers as organic nitrogen mineralization is active.",
            topDressing = "Apply Potassium Nitrate (13-0-45) foliar spray @ 5g/L during crop maturity to enhance stem rigidity against lodging.",
            micronutrientCare = "Apply Copper Sulphate (CuSO4) @ 4 kg/acre and Agricultural Lime @ 300 kg/acre to counteract toxic organic acids.",
            irrigationGuide = "Manage water exit rather than intake: dig deep peripheral drainage trenches (1.5 ft depth) around boundaries to lower water tables.",
            commonProblemsAndFix = "Problem: Crop lodging, hydrogen sulphide toxicity, and iron toxicity. Solution: Alternate wetting and drying (AWD), trench aeration, and lime additions.",
            themeColor = Color(0xFF047857),
            localImageRes = R.drawable.marshy_soil
        ),
        SoilTypeData(
            id = "forest_soil",
            name = "Forest & Hill Loam Soil",
            localName = "అటవీ నేలలు / పర్వత నేలలు",
            phRange = "5.5 - 6.5 (Slightly Acidic)",
            texture = "Loose, porous silty loam rich in leaf mould and partially decomposed woodland humus",
            waterHolding = "High with natural sponge-like absorption",
            drainage = "Good surface drainage on slopes; sensitive to topsoil erosion",
            organicCarbon = "High (1.2% - 2.5%)",
            majorNutrients = "Rich in organic Nitrogen and Potash; moderate in Phosphorus; high biological activity",
            topCrops = listOf(
                "Ginger & Turmeric / అల్లం, పసుపు",
                "Black Pepper / మిరియాలు",
                "Cardamom / యాలకులు",
                "Citrus Orchards / నిమ్మ, బత్తాయి",
                "Tea & Coffee / టీ, కాఫీ",
                "Vegetables in shaded polyhouses"
            ),
            secondaryCrops = listOf("Vanilla", "Nutmeg / జాజికాయ", "Clove / లవంగం", "Arecanut / పోక"),
            basalFertilizer = "Apply Neem Cake @ 200 kg/acre + 40 kg SSP + 20 kg Potash. Natural mycorrhizal biofertilizers boost nutrient uptake.",
            topDressing = "Apply enriched compost and vermicompost with Trichoderma viride twice a year around tree driplines.",
            micronutrientCare = "Spray Magnesium Sulphate @ 5g/L and Borax @ 1.5g/L to prevent premature fruit/berry drop in orchards and spices.",
            irrigationGuide = "Micro-sprinklers or basin drip. Construct contour bunds and stone pitched terraces along slopes to prevent topsoil loss during rains.",
            commonProblemsAndFix = "Problem: Rapid soil erosion and fungal damp-off during monsoons. Solution: Contour cultivation, vetiver grass hedges, and bio-fungicide drenching.",
            themeColor = Color(0xFF15803D),
            webImageUrl = "https://images.unsplash.com/photo-1448375240586-882707db888b?q=80&w=400&auto=format&fit=crop"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilListScreen(
    onBack: () -> Unit,
    onSoilClick: (soilId: String) -> Unit
) {
    val darkForest = Color(0xFF0F3820)
    val pageBg = Color(0xFFF4F7F4)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Soil Types & Crop Fit", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkForest)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(pageBg)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(SoilCatalog.allSoils) { soil ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSoilClick(soil.id) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            if (soil.localImageRes != null) {
                                Image(
                                    painter = painterResource(id = soil.localImageRes),
                                    contentDescription = soil.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(62.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                )
                            } else if (soil.webImageUrl != null) {
                                AsyncImage(
                                    model = ImageRequest.Builder(context)
                                        .data(soil.webImageUrl)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = soil.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(62.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Text(
                                    text = soil.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Text(
                                    text = soil.localName,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "pH: ${soil.phRange}",
                                    fontSize = 11.sp,
                                    color = soil.themeColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open Details",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}