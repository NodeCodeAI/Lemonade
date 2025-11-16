package com.ndroid.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    // États de l'application
    // 0 = citronnier, 1 = citron, 2 = verre de citronnade, 3 = verre vide
    var step by remember { mutableStateOf(0) }

    // Nombre de pressions nécessaires (aléatoire entre 2 et 4)
    var squeezeCount by remember { mutableStateOf(0) }
    var currentSqueeze by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Déterminer l'image à afficher selon l'étape
        val imageResource = when (step) {
            0 -> R.drawable.lemon_tree
            1 -> R.drawable.lemon_squeeze
            2 -> R.drawable.lemon_drink
            3 -> R.drawable.lemon_restart  // Image du verre vide
            else -> R.drawable.lemon_tree
        }

        // Déterminer le texte à afficher selon l'étape
        val textResource = when (step) {
            0 -> R.string.tap_tree
            1 -> R.string.squeeze_lemon
            2 -> R.string.drink_lemonade
            3 -> R.string.empty_glass
            else -> R.string.tap_tree
        }

        // Déterminer la description de contenu pour l'accessibilité
        val contentDescriptionResource = when (step) {
            0 -> R.string.lemon_tree
            1 -> R.string.lemon_squeeze
            2 -> R.string.lemon_drink
            3 -> R.string.empty_glass
            else -> R.string.lemon_tree
        }

        // Texte principal avec compteur de pressions
        Text(
            text = if (step == 1) {
                "${stringResource(textResource)} ($currentSqueeze/$squeezeCount)"
            } else {
                stringResource(textResource)
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Image interactive
        Image(
            painter = painterResource(imageResource),
            contentDescription = stringResource(contentDescriptionResource),
            modifier = Modifier
                .size(250.dp)
                .clickable {
                    when (step) {
                        0 -> {
                            // Étape 0 : Choisir un citron
                            // Générer un nombre aléatoire de pressions entre 2 et 4
                            squeezeCount = Random.nextInt(2, 5)
                            currentSqueeze = 0
                            step = 1
                        }
                        1 -> {
                            // Étape 1 : Presser le citron
                            currentSqueeze++
                            if (currentSqueeze >= squeezeCount) {
                                // Passer à l'étape suivante quand le citron est assez pressé
                                step = 2
                            }
                        }
                        2 -> {
                            // Étape 2 : Boire la citronnade → passer au verre vide
                            step = 3
                        }
                        3 -> {
                            // Étape 3 : Verre vide → recommencer au début
                            step = 0
                        }
                    }
                }
        )



    }
}