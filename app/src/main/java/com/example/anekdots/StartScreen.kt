package com.example.anekdots

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.anekdots.Parsing.ParseManager
import com.example.anekdots.ui.theme.*
import kotlin.concurrent.thread
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
    }
    return false
}

@Composable
fun start_sc(navHostController: NavHostController, applicationContext: Context) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(colors = listOf(Brown, BrownMain, Brown)))
        .padding(top = 20.dp), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier
            .padding(20.dp)
            .width(300.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .height(138.dp)
                )
            {
                createSingleBox(title = "Избранные", true, navHostController)
            }
            Row(
                Modifier
                    .size(width = 360.dp, height = 138.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                    dualBoxes(title = "по темам", btnType = false, navHostController, 0, applicationContext)
                    dualBoxes(title = "лучшие", true, navHostController, 1, applicationContext)
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .size(width = 380.dp, height = 138.dp)
            )
            {
                createSingleBox(title = "о приложении", false, navHostController)
            }
            Row(
                Modifier
                    .size(width = 360.dp, height = 138.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                dualBoxes(title = "сайт", true, navHostController, 2, applicationContext)
                dualBoxes(title = "случайные", false, navHostController, 3, applicationContext)
            }
        }
    }
}


@Composable
fun dualBoxes(
    title: String,
    btnType: Boolean,
    navHostController: NavHostController,
    funType: Int,
    applicationContext: Context
) {
    val nconnectstate = remember {
        mutableStateOf(false)
    }
    Box(
        Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(15.dp), ambientColor = BrownDark)
            .clip(RoundedCornerShape(15.dp))) {
        Image(
            imageVector = ImageVector.vectorResource(
                id = if(btnType){R.drawable.btn_new_sqr1}
                else{R.drawable.figm1btn}
            ),
            contentDescription = null,
            Modifier
                .fillMaxSize()
                .clickable {
                    val hasConnection = isOnline(context = applicationContext)
                    if (hasConnection) {
                        if (funType == 0) {
                            navHostController.navigate(Screen.MenuScreen.route)
                        } else if (funType == 1) {
                            val index = -3
                            val call = "${Screen.AnekdotScreen.route}/" + index
                            navHostController.navigate(call)
                        } else if (funType == 2) {
                            val browse =
                                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.anekdot.ru"))
                            startActivity(
                                applicationContext,
                                browse.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                                null
                            )
                        } else if (funType == 3) {
                            val index = -1
                            val call = "${Screen.AnekdotScreen.route}/" + index
                            navHostController.navigate(call)
                        }
                    } else {
                        nconnectstate.value = true
                    }
                },
            contentScale = ContentScale.FillBounds)
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = 15.dp),
            contentAlignment = Alignment.TopCenter) {
            Text(text = title, fontSize = 18.sp, color = BrownDark, fontWeight = FontWeight.Bold)
        }
    }
    if(nconnectstate.value) {
        noConnectDialog(
            title = "Ой, что-то не то!",
            desc = "Не установленно соединение с сетью." + "\n"
                    + "Убедитесь что устройство подключено к сети интернет, после чего попробуйте снова.",
            onDismiss = {
                nconnectstate.value = false
            }
        )
    }
}

fun chooseButton(type: Boolean): Int{
    if (type){
        return R.drawable.icon_btn_sq_df
    }
    return R.drawable.btn_new_sqr
}

@Composable
fun createSingleBox(title: String, type: Boolean, navHostController: NavHostController) {
    var showDiaolog = remember{ mutableStateOf(false) }
    Box(Modifier.padding(horizontal = 20.dp)){
        val imageModdifier = Modifier
            .fillMaxSize()
            .shadow(8.dp, shape = RoundedCornerShape(15.dp), ambientColor = BrownDark)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                if (type) {
                    val index = -2
                    val call = "${Screen.AnekdotScreen.route}/" + index
                    navHostController.navigate(call)
                } else {
                    showDiaolog.value = true
                }
            }
        if(type) {
            Image(
                painter = painterResource(id = R.drawable.tab_btn),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = imageModdifier
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp), contentAlignment = Alignment.CenterStart) {
                Text(text = title, fontSize = 18.sp, color = BrownDark, fontWeight = FontWeight.Bold)
            }
        }else{
            Image(
                painter = painterResource(id = R.drawable.tab_btn_180),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = imageModdifier
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(end = 12.dp), contentAlignment = Alignment.CenterEnd) {
                Text(text = title, fontSize = 18.sp, color = BrownDark, fontWeight = FontWeight.Bold)
            }
        }
    }
    if(showDiaolog.value == true){
        infoDialog(onDismiss = {isShowing: Boolean ->  showDiaolog.value = isShowing})
    }
}