package com.example.a209816_azmil_nazatul_project02.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
// IMPORTANT: Manually add your project's R import here
import com.example.a209816_azmil_nazatul_project02.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val abhayaLibreFont = FontFamily(
    Font(
        googleFont = GoogleFont("Abhaya Libre"),
        fontProvider = provider,
    )
)

val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = abhayaLibreFont),
    displayMedium = baseline.displayMedium.copy(fontFamily = abhayaLibreFont),
    displaySmall = baseline.displaySmall.copy(fontFamily = abhayaLibreFont),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = abhayaLibreFont),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = abhayaLibreFont),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = abhayaLibreFont),
    titleLarge = baseline.titleLarge.copy(fontFamily = abhayaLibreFont),
    titleMedium = baseline.titleMedium.copy(fontFamily = abhayaLibreFont),
    titleSmall = baseline.titleSmall.copy(fontFamily = abhayaLibreFont),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = abhayaLibreFont),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = abhayaLibreFont),
    bodySmall = baseline.bodySmall.copy(fontFamily = abhayaLibreFont),
    labelLarge = baseline.labelLarge.copy(fontFamily = abhayaLibreFont),
    labelMedium = baseline.labelMedium.copy(fontFamily = abhayaLibreFont),
    labelSmall = baseline.labelSmall.copy(fontFamily = abhayaLibreFont),
)