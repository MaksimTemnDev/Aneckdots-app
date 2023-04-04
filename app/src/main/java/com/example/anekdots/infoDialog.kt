package com.example.anekdots

import android.graphics.Paint.Align
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anekdots.ui.theme.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun infoDialog(onDismiss:(Boolean)->Unit){
    Dialog(onDismissRequest = { onDismiss(false)},
    properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(shape = RoundedCornerShape(20.dp), modifier = Modifier
            .fillMaxWidth(0.92f)
            .fillMaxHeight(0.67f)) {
            Column(modifier =
            Modifier
                .fillMaxWidth()
                .background(color = BeidgeWhite)
                .padding(horizontal = 15.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "О приложении", style = MaterialTheme.typography.h6, textAlign = TextAlign.Center)
                //I know that here should be weight, but something got wrong( thats why i use fillMaxHeight (mayby will fix in future)
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(15.dp)) {
                    Text(text = "В данном приложении собраны различные анекдоты," +
                            " которые удобно распределены по различным разделам данного приложения," +
                            " если вы хотите прочитать выборку случайных анекдотов," +
                            " перейдите в раздел 'случайные' (в правой нижней части экрана)." +
                            " Нажав на кнопку 'сайт' (находится слева внизу), вы попадёте на сайт aneckdot.ru," +
                            " с которого и загружается весь контент" +
                            " Нажав на кнопку 'избранные' (находится сверху), вы попадёте на подборку анекдотов, которые вы лайкнули ранее." +
                            " Если вы хотите посмотреть лучшие анекдоты за прошлые годы" +
                            " вы можете нажать на кнопку 'лучшие' (находится по центру справа)." +
                            " Для того чтобы выбрать анекдоты по конретным темам" +
                            " нажмите на кнопку 'по темам', вы попадёте на список тем" +
                            " далее выбираете нужную и получаете подборку анекдотов"
                        , fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(BrownMain)
                                .padding(5.dp)
                                .clickable {

                                }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "Back",
                                tint = BeidgeMiddle,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Box() {
                            Text(text = "Нажмите эту кнопку чтобы перейти к предыдущему анекдоту", fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(BrownMain)
                                .padding(5.dp)
                                .clickable {

                                }) {
                            Icon(
                                painter = painterResource(id = R.drawable.content_copy),
                                contentDescription = "Back",
                                tint = BeidgeMiddle,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Box() {
                            Text(text = "Нажмите эту кнопку чтобы скопировать анекдот", fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(BrownMain)
                                .padding(5.dp)
                                .clickable {

                                }) {
                            Icon(
                                painter = painterResource(id = R.drawable.favorite),
                                contentDescription = "Back",
                                tint = BeidgeMiddle,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Box() {
                            Text(text = "Нажмите эту кнопку чтобы добавить анекдот в избранные", fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(BrownMain)
                                .padding(5.dp)
                                .clickable {

                                }) {
                            Icon(
                                painter = painterResource(id = R.drawable.author),
                                contentDescription = "Back",
                                tint = BeidgeMiddle,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Box() {
                            Text(text = "Нажмите эту кнопку чтобы перейти к профелю автора", fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(BrownMain)
                                .padding(5.dp)
                                .clickable {

                                }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_forward),
                                contentDescription = "Back",
                                tint = BeidgeMiddle,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Box() {
                            Text(text = "Нажмите эту кнопку чтобы перейти к следующему анекдоту", fontWeight = FontWeight.Bold)
                        }
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(BrownMain)
                                .padding(5.dp)
                                .clickable {

                                }) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = "Back",
                                tint = BeidgeMiddle,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Box() {
                            Text(text = "Нажмите эту кнопку чтобы удалить анекдот  из избранных", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { onDismiss(false) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Brown, contentColor = LightBeidge),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = CircleShape) {
                        Text(text = "Понятно",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}