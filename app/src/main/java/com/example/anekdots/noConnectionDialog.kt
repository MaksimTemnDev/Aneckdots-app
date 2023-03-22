package com.example.anekdots

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.window.Dialog
import android.graphics.Paint.Align
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anekdots.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun noConnectDialog(
                    title: String?="Message",
                    desc: String?="Your Message",
                    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = BeidgeMain,
                        shape = RoundedCornerShape(
                            topStart = 25.dp,
                            bottomStart = 5.dp,
                            topEnd = 25.dp,
                            bottomEnd = 5.dp
                        )
                    )
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            ) {
                Image(painter = painterResource(id = R.drawable.ncback),
                    contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(
                        topStart = 25.dp,
                        topEnd = 25.dp))
                    )
                Image(
                    painter = painterResource(id = R.drawable.noconnection),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .padding(top = 8.dp),

                    )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(75.dp))

                    //.........................Text: title
                    Text(
                        text = title!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h4,
                        color = Brown,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h5,
                        color = Brown,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(5.dp))

                    //.........................Button : OK button
                    Button(
                        onClick = onDismiss,
                        shape = Shapes.small,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(25.dp)),
                        colors= ButtonDefaults.buttonColors(backgroundColor = Brown, contentColor = LightBeidge),
                        //.clip(RoundedCornerShape(25.dp))
                    ) {
                        Text(
                            text = "Хорошо",
                            style = MaterialTheme.typography.h5,
                        )
                    }



                    //.........................Spacer
                    Spacer(modifier = Modifier.height(10.dp))

                }


            }

        }
    }
}

@Composable
@Preview
fun showDialog(){
    val bul = remember { mutableStateOf(true)}
    if(bul.value){
        noConnectDialog(
            title = "Ой, что-то не то!",
            desc = "No Internet Connection found.\n" +
                    "Check your connection or try again.",
            onDismiss = {
                bul.value = false
            }
        )
    }
}