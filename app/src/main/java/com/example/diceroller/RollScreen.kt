package com.example.diceroller

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

//DiceRollerTopBar function
@Composable
fun DiceRollerTopBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        title = { Text(text = "Dice Roller", style = MaterialTheme.typography.h4, color = MaterialTheme.colors.primary,) },
    )
}

//DiceRollerApp function
@Composable
fun DiceRollerApp() {
    var resultColor by remember {
        mutableStateOf(1)
    }
    resultColor = (1..3).random()
    val colorResource = when (resultColor) {
        1 -> MaterialTheme.colors.primary
        2 -> MaterialTheme.colors.secondary
        3 -> MaterialTheme.colors.primaryVariant
        else -> MaterialTheme.colors.primary
    }

    Scaffold(
        topBar = {
            DiceRollerTopBar()
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(colorResource)) {
            DiceWithButtonAndImage(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .background(colorResource)
            )
        }
    }

}

//DiceWithButtonAndImage function
@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    var result by remember {
        mutableStateOf(1)
    }
    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    val intentContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = result.toString()
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Row() {
            Button(
                onClick = { result = (1..6).random() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary, backgroundColor = MaterialTheme.colors.secondaryVariant),
            ) {
                Text(
                    text = stringResource(R.string.roll),
                    fontSize = 24.sp
                )
            }
            Spacer(
                modifier = Modifier.width(5.dp)
            )
            Button(
                onClick = { result = 1 },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary, backgroundColor = MaterialTheme.colors.secondaryVariant),
            ) {
                Text(
                    text = stringResource(R.string.reset),
                    fontSize = 24.sp
                )
            }
        }
        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Button(
            onClick = { shareCityCardInformation(intentContext = intentContext, result = result) },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary, backgroundColor = MaterialTheme.colors.secondaryVariant),
        ) {
            Text(
                text = "SHARE RESULT",
                fontSize = 24.sp
            )
        }
    }
}

//shareCityCardInformation private function
@SuppressLint("ResourceType")
private fun shareCityCardInformation(intentContext: Context, result: Int) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            ("Hi!!! Look what I got by rolling the dice:\n" + "I did " + result + "!!!")
        )
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        ContextCompat.startActivity(intentContext, shareIntent, null)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            intentContext,
            ("ERROR"),
            Toast.LENGTH_LONG
        ).show()
    }
}
