package com.example.agridevelop

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

data class CropItem(
    val name: String,
    val localName: String,
    val imageUrl: String
)

data class SoilRichDetails(
    val temperatureRange: String,
    val waterRetentionLevel: String,
    val crops: List<CropItem>,
    val tips: List<String>,
    val traditionalMethods: List<String>,
    val modernMethods: List<String>,
    val fertilizerGuide: List<String>,
    val seasonalCrops: List<String>
)

object SoilDetailedContent {
    fun getDetails(soilId: String): SoilRichDetails {
        return when (soilId) {
            "black_soil" -> SoilRichDetails(
                temperatureRange = "25°C - 35°C",
                waterRetentionLevel = "High",
                crops = listOf(
                    CropItem("Cotton", "పత్తి", "https://images.unsplash.com/photo-1606041008023-472dfb5e530f?w=200&auto=format&fit=crop"),
                    CropItem("Soybean", "సోయాబీన్", "https://images.unsplash.com/photo-1599420186946-7b6fb4e297f0?w=200&auto=format&fit=crop"),
                    CropItem("Chilli", "మిరప", "https://images.unsplash.com/photo-1588252303782-cb80119abd6d?w=200&auto=format&fit=crop"),
                    CropItem("Bengal Gram", "శనగ", "https://images.unsplash.com/photo-1515543237350-b3eea1ec8082?w=200&auto=format&fit=crop"),
                    CropItem("Groundnut", "వేరుశనగ", "https://images.unsplash.com/photo-1567894340315-735d7c361db0?w=200&auto=format&fit=crop"),
                    CropItem("Wheat", "గోధుమ", "https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?w=200&auto=format&fit=crop"),
                    CropItem("Sunflower", "పొద్దుతిరుగుడు", "https://images.unsplash.com/photo-1597848212624-a19eb35e2651?w=200&auto=format&fit=crop"),
                    CropItem("Jowar", "జొన్న", "https://images.unsplash.com/photo-1586201375761-83865001e31c?w=200&auto=format&fit=crop"),
                    CropItem("Paddy", "వరి", "https://images.unsplash.com/photo-1536657464919-892534f60d6e?w=200&auto=format&fit=crop"),
                    CropItem("Tobacco", "పొగాకు", "https://images.unsplash.com/photo-1500651230702-0e2d8a49d4ad?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "Deep summer ploughing opens wide cracks to kill subterranean pests and insect pupae.",
                    "Avoid entering the field when soil is soaking wet to prevent extreme clodding and root damage.",
                    "Plant early in the Kharif season to take advantage of natural residual subsoil moisture."
                ),
                traditionalMethods = listOf(
                    "Application of well-cured sheep penning (గొర్రెల మంద) for organic nitrogen enrichment.",
                    "Broad bed and furrow (BBF) ploughing to allow natural runoff during sudden heavy downpours.",
                    "Intercropping cotton with pigeon pea (కంది) in a 4:1 ratio for pest trapping and nitrogen fixation."
                ),
                modernMethods = listOf(
                    "Sub-surface drip irrigation to prevent surface salt build-up and reduce evaporation loss by 45%.",
                    "Laser leveling to avoid stagnation pockets that trigger Phytophthora seedling blight.",
                    "Tractor-mounted seed-cum-fertilizer drills for uniform placement in the moist seed zone."
                ),
                fertilizerGuide = listOf(
                    "Basal: 40-50 kg DAP + 25 kg MOP per acre at final land preparation.",
                    "Top-dressing: Split Nitrogen doses (Urea) at 30 and 60 DAS under sufficient moisture.",
                    "Micronutrients: 10-15 kg Zinc Sulphate (21%) per acre once every 2 years to counter zinc lock."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Cotton (పత్తి), Soybean (సోయాబీన్), Red Gram (కంది), Maize (మొక్కజొన్న).",
                    "Rabi (Winter: Oct - Feb): Bengal Gram / Chickpea (శనగ), Rabi Jowar (జొన్న), Wheat (గోధుమ), Safflower (కుసుమ).",
                    "Zaid (Summer: Feb - May): Sesame (నువ్వులు), Sunflower (పొద్దుతిరుగుడు), Green Gram (పెసలు) where borewell irrigation is assured."
                )
            )

            "red_soil" -> SoilRichDetails(
                temperatureRange = "22°C - 34°C",
                waterRetentionLevel = "Low to Medium",
                crops = listOf(
                    CropItem("Groundnut", "వేరుశనగ", "https://images.unsplash.com/photo-1567894340315-735d7c361db0?w=200&auto=format&fit=crop"),
                    CropItem("Maize", "మొక్కజొన్న", "https://images.unsplash.com/photo-1551754655-cd27e38d2076?w=200&auto=format&fit=crop"),
                    CropItem("Red Gram", "కంది", "https://images.unsplash.com/photo-1586201375761-83865001e31c?w=200&auto=format&fit=crop"),
                    CropItem("Castor", "ఆముదం", "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?w=200&auto=format&fit=crop"),
                    CropItem("Tomato", "టమోటా", "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=200&auto=format&fit=crop"),
                    CropItem("Onion", "ఉల్లి", "https://images.unsplash.com/photo-1508747703725-719777637510?w=200&auto=format&fit=crop"),
                    CropItem("Bajra", "సజ్జలు", "https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?w=200&auto=format&fit=crop"),
                    CropItem("Sesame", "నువ్వులు", "https://images.unsplash.com/photo-1515543237350-b3eea1ec8082?w=200&auto=format&fit=crop"),
                    CropItem("Ragi", "రాగులు", "https://images.unsplash.com/photo-1599420186946-7b6fb4e297f0?w=200&auto=format&fit=crop"),
                    CropItem("Cowpea", "అలసందలు", "https://images.unsplash.com/photo-1500651230702-0e2d8a49d4ad?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "Incorporate tank silt (చెరువు మట్టి) to improve water retention and soil texture.",
                    "Mulch rows using groundnut shells or dry biomass to minimize rapid soil moisture evaporation.",
                    "Split nitrogen into 3-4 small applications to prevent leaching losses."
                ),
                traditionalMethods = listOf(
                    "Carting composted farmyard manure (FYM) @ 5 tonnes/acre before monsoon onset.",
                    "Ploughing across the slope (Contour ploughing) to arrest rainwater runoff.",
                    "Mixed cropping with cowpea or green gram to preserve humidity."
                ),
                modernMethods = listOf(
                    "Drip fertigation with water-soluble 19:19:19 to feed roots directly.",
                    "Plastic film mulching for vegetable cultivation to prevent weed emergence and moisture loss.",
                    "Foliar feeding of micro-nutrients during vegetative and flowering transitions."
                ),
                fertilizerGuide = listOf(
                    "Basal: 50 kg Single Super Phosphate (SSP) + 20 kg Potash per acre.",
                    "Gypsum: 200 kg/acre at 40-45 DAS for pod formation in groundnut.",
                    "Foliar Boron: Spray 0.2% Borax at flowering to boost pollination and fruit set."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Groundnut (వేరుశనగ), Castor (ఆముదం), Red Gram (కంది), Millets / Ragi (రాగులు, కొర్రలు).",
                    "Rabi (Winter: Oct - Feb): Mustard (ఆవాలు), Horse Gram (ఉలవలు), Rabi Groundnut, Tomato & Brinjal (టమోటా, వంకాయ).",
                    "Zaid (Summer: Feb - May): Watermelon (పుచ్చ), Muskmelon (కర్బూజ), Cowpea (అలసందలు), Cluster Beans (గోరుచిక్కుడు)."
                )
            )

            "alluvial_soil" -> SoilRichDetails(
                temperatureRange = "20°C - 32°C",
                waterRetentionLevel = "High",
                crops = listOf(
                    CropItem("Paddy", "వరి", "https://images.unsplash.com/photo-1536657464919-892534f60d6e?w=200&auto=format&fit=crop"),
                    CropItem("Sugarcane", "చెరకు", "https://images.unsplash.com/photo-1589367920969-ab8e050bbb04?w=200&auto=format&fit=crop"),
                    CropItem("Banana", "అరటి", "https://images.unsplash.com/photo-1528825871115-3581a5387919?w=200&auto=format&fit=crop"),
                    CropItem("Turmeric", "పసుపు", "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=200&auto=format&fit=crop"),
                    CropItem("Wheat", "గోధుమ", "https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?w=200&auto=format&fit=crop"),
                    CropItem("Potato", "బంగాళాదుంప", "https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=200&auto=format&fit=crop"),
                    CropItem("Papaya", "బొప్పాయి", "https://images.unsplash.com/photo-1517282009859-f000ec3b26fe?w=200&auto=format&fit=crop"),
                    CropItem("Mustard", "ఆవాలు", "https://images.unsplash.com/photo-1508747703725-719777637510?w=200&auto=format&fit=crop"),
                    CropItem("Jute", "జనపనార", "https://images.unsplash.com/photo-1500651230702-0e2d8a49d4ad?w=200&auto=format&fit=crop"),
                    CropItem("Vegetables", "కూరగాయలు", "https://images.unsplash.com/photo-1597362925123-77861d3fbac7?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "Cultivate green manure crops (Dhaincha or Sunnhemp) before the main paddy season.",
                    "Maintain continuous monitoring of soil pH to avoid gradual salinization under canal irrigation.",
                    "Alternate puddle paddy with dryland pulses to break subsurface hardpans."
                ),
                traditionalMethods = listOf(
                    "Field ponding with river silt runoff during peak flood periods.",
                    "Deep summer ploughing followed by 15-day solar drying.",
                    "Vermicompost layering around high-value banana basins."
                ),
                modernMethods = listOf(
                    "System of Rice Intensification (SRI) to save water and increase tillering.",
                    "Sensor-based AWD (Alternate Wetting and Drying) tube monitoring in paddy fields.",
                    "Micro-sprinklers for nursery seedling germination and vegetables."
                ),
                fertilizerGuide = listOf(
                    "Basal: 50 kg DAP + 30 kg MOP + 10 kg Zinc Sulphate per acre.",
                    "Top dressing: 3 split doses of Neem-coated Urea at 21, 42, and 60 DAS.",
                    "Biofertilizers: Azospirillum and Phosphobacteria seed drenching."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Paddy (వరి - MTU 1010/BPT 5204), Jute (జనపనార), Maize (మొక్కజొన్న), Turmeric (పసుపు).",
                    "Rabi (Winter: Oct - Feb): Wheat (గోధుమ), Potato (బంగాళాదుంప), Mustard (ఆవాలు), Tobacco (పొగాకు).",
                    "Zaid (Summer: Feb - May): Sugarcane (చెరకు - year-round), Banana (అరటి), Bitter Gourd & Cucumber (కాకర, దోస)."
                )
            )

            "laterite_soil" -> SoilRichDetails(
                temperatureRange = "24°C - 36°C",
                waterRetentionLevel = "Low",
                crops = listOf(
                    CropItem("Cashew", "జీడిమామిడి", "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=200&auto=format&fit=crop"),
                    CropItem("Mango", "మామిడి", "https://images.unsplash.com/photo-1553279768-865429fa0078?w=200&auto=format&fit=crop"),
                    CropItem("Ginger", "అల్లం", "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=200&auto=format&fit=crop"),
                    CropItem("Coffee", "కాఫీ", "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=200&auto=format&fit=crop"),
                    CropItem("Tapioca", "కర్రపెండలం", "https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=200&auto=format&fit=crop"),
                    CropItem("Rubber", "రబ్బర్", "https://images.unsplash.com/photo-1500651230702-0e2d8a49d4ad?w=200&auto=format&fit=crop"),
                    CropItem("Arecanut", "పోకచెక్క", "https://images.unsplash.com/photo-1528825871115-3581a5387919?w=200&auto=format&fit=crop"),
                    CropItem("Pineapple", "అనాస", "https://images.unsplash.com/photo-1550258987-190a2d41a8ba?w=200&auto=format&fit=crop"),
                    CropItem("Coconut", "కొబ్బరి", "https://images.unsplash.com/photo-1544376798-89aa6b82c6cd?w=200&auto=format&fit=crop"),
                    CropItem("Sweet Potato", "చిలగడదుంప", "https://images.unsplash.com/photo-1596040033229-a9821ebd058d?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "Soil is acidic: Agricultural Lime (సున్నం) application is essential once every 3 years.",
                    "Avoid water-soluble phosphates as iron locks phosphorus; use Rock Phosphate instead.",
                    "Build terrace edges with rock pitching to stop soil wash on sloped topography."
                ),
                traditionalMethods = listOf(
                    "Adding crushed wood ash (బూడిద) to tree basins to neutralize acidity.",
                    "Heavy mulching of orchard tree circles with dried cashew/forest leaves.",
                    "Constructing continuous contour trenches across red gravel ridges."
                ),
                modernMethods = listOf(
                    "Soil reclamation using Dolomite based on specific soil testing results.",
                    "Inline pressure compensating drip systems for orchard slope irrigation.",
                    "Foliar sprays of complex micronutrients (Zinc, Boron, Magnesium)."
                ),
                fertilizerGuide = listOf(
                    "Lime: Apply 200-250 kg Agricultural Lime/Dolomite per acre 1 month before sowing.",
                    "Phosphorus: 100 kg Mussoorie Rock Phosphate (MRP) mixed with compost.",
                    "Potash: 30 kg Potassium Schoenite or MOP applied in two splits."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Ginger (అల్లం), Turmeric (పసుపు), Tapioca (కర్రపెండలం), Sweet Potato (చిలగడదుంప).",
                    "Rabi (Winter: Oct - Feb): Black Pepper (మిరియాలు), Arecanut (పోకచెక్క), Coffee (కాఫీ), Pulses on ridges.",
                    "Zaid (Summer: Feb - May): Cashew (జీడిమామిడి harvest), Mango (మామిడి harvest), Pineapple (అనాస)."
                )
            )

            "saline_soil" -> SoilRichDetails(
                temperatureRange = "25°C - 38°C",
                waterRetentionLevel = "Poor / Waterlogging",
                crops = listOf(
                    CropItem("Salt Paddy", "చౌడు వరి", "https://images.unsplash.com/photo-1536657464919-892534f60d6e?w=200&auto=format&fit=crop"),
                    CropItem("Dhaincha", "జీలుగ", "https://images.unsplash.com/photo-1500651230702-0e2d8a49d4ad?w=200&auto=format&fit=crop"),
                    CropItem("Cotton", "పత్తి", "https://images.unsplash.com/photo-1606041008023-472dfb5e530f?w=200&auto=format&fit=crop"),
                    CropItem("Barley", "బార్లీ", "https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?w=200&auto=format&fit=crop"),
                    CropItem("Mustard", "ఆవాలు", "https://images.unsplash.com/photo-1508747703725-719777637510?w=200&auto=format&fit=crop"),
                    CropItem("Sugarbeet", "బీట్‌రూట్", "https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=200&auto=format&fit=crop"),
                    CropItem("Guar", "గోరుచిక్కుడు", "https://images.unsplash.com/photo-1599420186946-7b6fb4e297f0?w=200&auto=format&fit=crop"),
                    CropItem("Spinach", "పాలకూర", "https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=200&auto=format&fit=crop"),
                    CropItem("Pomegranate", "దానిమ్మ", "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=200&auto=format&fit=crop"),
                    CropItem("Fodder Grass", "పశుగ్రాసం", "https://images.unsplash.com/photo-1515543237350-b3eea1ec8082?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "Reclamation with Gypsum followed by heavy clean water leaching is required.",
                    "Never let the topsoil dry completely; drying pulls salts up to the surface.",
                    "Select certified salt-tolerant seed varieties (e.g. CSR series for paddy)."
                ),
                traditionalMethods = listOf(
                    "Deep trenching on field boundaries to direct and flush away saline water.",
                    "Cultivating green manure Dhaincha for 45 days and ploughing it directly into wet soil.",
                    "Applying pressmud (చెరకు ఫ్యాక్టరీ వ్యర్థం) from sugar mills."
                ),
                modernMethods = listOf(
                    "Subsurface perforated pipe drainage (SSD) to permanently drain dissolved salts.",
                    "Soil electrical conductivity (EC) measurement before every planting season.",
                    "Application of elemental sulphur to reduce pH in alkaline crusts."
                ),
                fertilizerGuide = listOf(
                    "Gypsum: Broadcast 1.5 - 2.5 tonnes/acre, flood for 48 hours and drain off.",
                    "Nitrogen: Use Ammonium Sulphate instead of Urea; provide 25% extra Nitrogen.",
                    "Micronutrients: 20 kg Zinc Sulphate + 10 kg Ferrous Sulphate per acre."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Salt-tolerant Paddy (CSR 23, CSR 36), Dhaincha (జీలుగ), Tolerant Cotton (పత్తి).",
                    "Rabi (Winter: Oct - Feb): Barley (బార్లీ), Salt-tolerant Mustard (ఆవాలు), Sugarbeet (బీట్‌రూట్), Oats.",
                    "Zaid (Summer: Feb - May): Rhodes grass, Bermuda grass (పశుగ్రాసం), Spinach (పాలకూర) under regular canal flushing."
                )
            )

            "sandy_soil" -> SoilRichDetails(
                temperatureRange = "22°C - 35°C",
                waterRetentionLevel = "Very Low",
                crops = listOf(
                    CropItem("Watermelon", "పుచ్చకాయ", "https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=200&auto=format&fit=crop"),
                    CropItem("Muskmelon", "కర్బూజ", "https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=200&auto=format&fit=crop"),
                    CropItem("Cucumber", "దోసకాయ", "https://images.unsplash.com/photo-1449339854873-750e6df51301?w=200&auto=format&fit=crop"),
                    CropItem("Groundnut", "వేరుశనగ", "https://images.unsplash.com/photo-1567894340315-735d7c361db0?w=200&auto=format&fit=crop"),
                    CropItem("Carrot", "క్యారెట్", "https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=200&auto=format&fit=crop"),
                    CropItem("Radish", "ముల్లంగి", "https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=200&auto=format&fit=crop"),
                    CropItem("Bottle Gourd", "సొరకాయ", "https://images.unsplash.com/photo-1597362925123-77861d3fbac7?w=200&auto=format&fit=crop"),
                    CropItem("Sweet Corn", "స్వీట్ కార్న్", "https://images.unsplash.com/photo-1551754655-cd27e38d2076?w=200&auto=format&fit=crop"),
                    CropItem("Bitter Gourd", "కాకర", "https://images.unsplash.com/photo-1586201375761-83865001e31c?w=200&auto=format&fit=crop"),
                    CropItem("Sesame", "నువ్వులు", "https://images.unsplash.com/photo-1515543237350-b3eea1ec8082?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "Requires frequent light waterings; flood irrigation results in massive water waste.",
                    "Apply composted coir pith to create water-sponge pockets around roots.",
                    "Use slow-release or polymer-coated fertilizers to reduce leaching into groundwater."
                ),
                traditionalMethods = listOf(
                    "Spreading riverbed alluvial tank silt (చెరువు మట్టి) to give clay body to sand.",
                    "Trench planting with thick organic cow dung linings.",
                    "Dry grass thatch mulching around vegetable vines."
                ),
                modernMethods = listOf(
                    "Automated daily drip fertigation delivering nutrients in small pulses.",
                    "Silver-black reflective polyethylene mulching for weed and moisture control.",
                    "Hydrogel crystals applied at the root zone during transplanting."
                ),
                fertilizerGuide = listOf(
                    "Basal: 6-8 tonnes well-decomposed FYM or composted poultry manure per acre.",
                    "Fertigation: Water-soluble 19:19:19 (3-4 kg/acre) applied every 4 days via drip.",
                    "Foliar Feeding: Weekly sprays of multi-micronutrient formulas to prevent sudden chlorosis."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Groundnut (వేరుశనగ), Sesame (నువ్వులు), Pearl Millet / Bajra (సజ్జలు), Cluster Bean (గోరుచిక్కుడు).",
                    "Rabi (Winter: Oct - Feb): Carrot (క్యారెట్), Radish (ముల్లంగి), Sweet Potato (చిలగడదుంప), Onion (ఉల్లి).",
                    "Zaid (Summer: Feb - May): Watermelon (పుచ్చకాయ), Muskmelon (కర్బూజ), Cucumber (దోసకాయ), Bottle Gourd (సొరకాయ)."
                )
            )

            "peaty_soil" -> SoilRichDetails(
                temperatureRange = "18°C - 30°C",
                waterRetentionLevel = "Very High (Waterlogged)",
                crops = listOf(
                    CropItem("Deepwater Paddy", "ముంపు వరి", "https://images.unsplash.com/photo-1536657464919-892534f60d6e?w=200&auto=format&fit=crop"),
                    CropItem("Jute", "జనపనార", "https://images.unsplash.com/photo-1500651230702-0e2d8a49d4ad?w=200&auto=format&fit=crop"),
                    CropItem("Colocasia", "చామదుంప", "https://images.unsplash.com/photo-1598170845058-32b9d6a5da37?w=200&auto=format&fit=crop"),
                    CropItem("Lotus", "తామర", "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?w=200&auto=format&fit=crop"),
                    CropItem("Elephant Yam", "కంద", "https://images.unsplash.com/photo-1596040033229-a9821ebd058d?w=200&auto=format&fit=crop"),
                    CropItem("Water Spinach", "కలమి శాకం", "https://images.unsplash.com/photo-1576045057995-568f588f82fb?w=200&auto=format&fit=crop"),
                    CropItem("Water Chestnut", "సింగార", "https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=200&auto=format&fit=crop"),
                    CropItem("Mint", "పుదీనా", "https://images.unsplash.com/photo-1597362925123-77861d3fbac7?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "High organic matter but high acidity: Lime application is critical.",
                    "Construct perimeter and cross-field drainage ditches to keep root zones aerated.",
                    "Apply extra potash to give crops strong stem resistance against lodging."
                ),
                traditionalMethods = listOf(
                    "Raised bed planting ridges above stagnant ground water levels.",
                    "Burning weed residues on field surfaces to supply ash and neutralize acidity.",
                    "Rotating wetland paddy with water-chestnut and aquatic greens."
                ),
                modernMethods = listOf(
                    "Siphon drainage pumps to quickly drain excess monsoon floods.",
                    "Soil aeration treatments using rippers during dry spells.",
                    "Bio-inoculation with cellulolytic fungi to balance peat decomposition."
                ),
                fertilizerGuide = listOf(
                    "Lime: 300 kg Agricultural Lime per acre broadcast 3 weeks before planting.",
                    "Potash: 40-50 kg MOP/acre (essential for peaty soil balance).",
                    "Micronutrients: 4-5 kg Copper Sulphate (CuSO4) per acre."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Deepwater Paddy (ముంపు వరి), Jute (జనపనార), Colocasia (చామదుంప), Elephant Yam (కంద).",
                    "Rabi (Winter: Oct - Feb): Post-drainage Boro Rice, Aquatic Greens, Mustard on raised ridges, Mint (పుదీనా).",
                    "Zaid (Summer: Feb - May): Water Chestnut (సింగార), Lotus root (తామర), Water Spinach."
                )
            )

            else -> SoilRichDetails(
                temperatureRange = "15°C - 28°C",
                waterRetentionLevel = "High",
                crops = listOf(
                    CropItem("Tea", "టీ", "https://images.unsplash.com/photo-1544787219-7f47ccb76574?w=200&auto=format&fit=crop"),
                    CropItem("Cardamom", "యాలకులు", "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=200&auto=format&fit=crop"),
                    CropItem("Black Pepper", "మిరియాలు", "https://images.unsplash.com/photo-1509316975850-ff9c5deb0cd9?w=200&auto=format&fit=crop"),
                    CropItem("Ginger", "అల్లం", "https://images.unsplash.com/photo-1615485290382-441e4d049cb5?w=200&auto=format&fit=crop"),
                    CropItem("Citrus", "నిమ్మ", "https://images.unsplash.com/photo-1534483509719-3feaee7c30da?w=200&auto=format&fit=crop"),
                    CropItem("Coffee", "కాఫీ", "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=200&auto=format&fit=crop"),
                    CropItem("Vanilla", "వెనిల్లా", "https://images.unsplash.com/photo-1500651230702-0e2d8a49d4ad?w=200&auto=format&fit=crop"),
                    CropItem("Nutmeg", "జాజికాయ", "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=200&auto=format&fit=crop"),
                    CropItem("Clove", "లవంగం", "https://images.unsplash.com/photo-1553279768-865429fa0078?w=200&auto=format&fit=crop")
                ),
                tips = listOf(
                    "Maintain continuous vegetation and tree canopy cover to prevent hill slope erosion.",
                    "Incorporate compost enriched with Trichoderma to prevent damping-off fungi.",
                    "Build stone check dams across water streams to slow hill runoff."
                ),
                traditionalMethods = listOf(
                    "Terrace farming along mountain contours.",
                    "Natural forest leaf litter composting inside tree trenches.",
                    "Shade management using multi-tier agroforestry trees."
                ),
                modernMethods = listOf(
                    "Micro-sprinkler systems providing humid mist during dry periods.",
                    "Bio-fertilizers (VAM mycorrhiza) for enhanced phosphorus absorption.",
                    "Soil moisture probes for high-value spice crops."
                ),
                fertilizerGuide = listOf(
                    "Organic: 200 kg Neem Cake + 2 tonnes vermicompost per acre.",
                    "Rock Phosphate: 50 kg/acre in two split doses.",
                    "Foliar: Spray Magnesium Sulphate @ 5g/L and Borax @ 1.5g/L during flowering."
                ),
                seasonalCrops = listOf(
                    "Kharif (Monsoon: June - Oct): Ginger (అల్లం), Turmeric (పసుపు), Cardamom planting, Off-season Hill Vegetables.",
                    "Rabi (Winter: Oct - Feb): Black Pepper harvesting (మిరియాలు), Coffee picking (కాఫీ), Citrus fruits (నిమ్మ / నారింజ).",
                    "Zaid (Summer: Feb - May): Tea flush plucking (టీ), Vanilla hand pollination, Clove drying (లవంగం)."
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilDetailScreen(
    soilId: String?,
    onBack: () -> Unit
) {
    val darkForest = Color(0xFF0F5A2C)
    val pageBg = Color(0xFFF8FAF8)
    val context = LocalContext.current

    val currentSoilId = soilId ?: "black_soil"
    val soil = SoilCatalog.allSoils.find { it.id.equals(currentSoilId, ignoreCase = true) }
        ?: SoilCatalog.allSoils.first()

    val details = SoilDetailedContent.getDetails(soil.id)

    // Expandable accordion states
    var tipsExpanded by remember { mutableStateOf(false) }
    var traditionalExpanded by remember { mutableStateOf(false) }
    var modernExpanded by remember { mutableStateOf(false) }
    var fertilizersExpanded by remember { mutableStateOf(false) }
    var seasonalExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "${soil.name.substringBefore("(").trim()} Details",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Hero Image Card with Pill Badge
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    if (soil.localImageRes != null) {
                        Image(
                            painter = painterResource(id = soil.localImageRes),
                            contentDescription = soil.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else if (soil.webImageUrl != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(soil.webImageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = soil.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Top-right pill label badge
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(14.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.88f)
                    ) {
                        Text(
                            text = soil.name.substringBefore("(").trim(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // 3-Column Metric Stat Cards (Temperature, pH Level, Water Retention)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricStatCard(
                    modifier = Modifier.weight(1f),
                    label = "Temperature",
                    value = details.temperatureRange,
                    icon = Icons.Default.DeviceThermostat,
                    iconTint = Color(0xFFE11D48)
                )
                MetricStatCard(
                    modifier = Modifier.weight(1f),
                    label = "pH Level",
                    value = soil.phRange.substringBefore("(").trim(),
                    icon = Icons.Default.Opacity,
                    iconTint = Color(0xFF0284C7)
                )
                MetricStatCard(
                    modifier = Modifier.weight(1f),
                    label = "Water Retention",
                    value = details.waterRetentionLevel,
                    icon = Icons.Default.WaterDrop,
                    iconTint = Color(0xFF059669)
                )
            }

            // Suitable Crops Section (Horizontal scrollable with circular crop pictures)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Suitable Crops",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B3824),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(details.crops) { crop ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(68.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(crop.imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = crop.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(58.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color(0xFFE2E8F0), CircleShape)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = crop.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1E293B)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Expandable Accordion 1: Tips for [Soil Name]
            ExpandableDetailCard(
                title = "Tips for ${soil.name.substringBefore("(").trim()}",
                icon = Icons.Default.Spa,
                iconTint = Color(0xFF15803D),
                isExpanded = tipsExpanded,
                onToggle = { tipsExpanded = !tipsExpanded },
                items = details.tips
            )

            // Expandable Accordion 2: Traditional Methods
            ExpandableDetailCard(
                title = "Traditional Methods",
                icon = Icons.Default.AccountBalance,
                iconTint = Color(0xFFB45309),
                isExpanded = traditionalExpanded,
                onToggle = { traditionalExpanded = !traditionalExpanded },
                items = details.traditionalMethods
            )

            // Expandable Accordion 3: Modern Methods
            ExpandableDetailCard(
                title = "Modern Methods",
                icon = Icons.Default.Apartment,
                iconTint = Color(0xFF0369A1),
                isExpanded = modernExpanded,
                onToggle = { modernExpanded = !modernExpanded },
                items = details.modernMethods
            )

            // Expandable Accordion 4: Suitable Fertilizers
            ExpandableDetailCard(
                title = "Suitable Fertilizers",
                icon = Icons.Default.Science,
                iconTint = Color(0xFF7C3AED),
                isExpanded = fertilizersExpanded,
                onToggle = { fertilizersExpanded = !fertilizersExpanded },
                items = details.fertilizerGuide
            )

            // Expandable Accordion 5: Suitable Crops Based on Season
            ExpandableDetailCard(
                title = "Suitable Crops Based on Season",
                icon = Icons.Default.CalendarMonth,
                iconTint = Color(0xFFD97706),
                isExpanded = seasonalExpanded,
                onToggle = { seasonalExpanded = !seasonalExpanded },
                items = details.seasonalCrops
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MetricStatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    iconTint: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.5.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color(0xFF64748B),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )
        }
    }
}

@Composable
private fun ExpandableDetailCard(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    items: List<String>
) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 90f else 0f, label = "arrowRotation")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onToggle() },
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B3824)
                    )
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(rotationAngle)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                    items.forEach { point ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "• ",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = iconTint
                            )
                            Text(
                                text = point,
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                color = Color(0xFF334155)
                            )
                        }
                    }
                }
            }
        }
    }
}