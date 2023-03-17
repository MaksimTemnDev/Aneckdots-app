package com.example.anekdots

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.example.anekdots.Parsing.ParseManager
import com.example.anekdots.ui.theme.*
import kotlin.concurrent.thread


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
    Box(
        Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(15.dp), ambientColor = BrownDark)
            .clip(RoundedCornerShape(15.dp))) {
        Image(painter = painterResource(id = chooseButton(btnType)), contentDescription = null,
            Modifier.fillMaxSize().clickable {
            if(funType == 0){
                navHostController.navigate(Screen.MenuScreen.route)
            } else if(funType == 1){
                val index = -3
                val call = "${Screen.AnekdotScreen.route}/" + index
                navHostController.navigate(call)
            } else if(funType == 2){
                val browse = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.anekdot.ru"))
                startActivity(applicationContext, browse.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), null)
            }else if(funType == 3){
                val index = -1
                val call = "${Screen.AnekdotScreen.route}/" + index
                navHostController.navigate(call)
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
}

fun chooseButton(type: Boolean): Int{
    if (type){
        return R.drawable.icon_btn_sq_df
    }
    return R.drawable.btn_new_sqr
}

@Composable
fun createSingleBox(title: String, type: Boolean, navHostController: NavHostController) {
    Box(Modifier.padding(horizontal = 20.dp)){
        val imageModdifier = Modifier
            .fillMaxSize()
            .shadow(8.dp, shape = RoundedCornerShape(15.dp), ambientColor = BrownDark)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                if(type){
                    val index = -2
                    val call = "${Screen.AnekdotScreen.route}/" + index
                    navHostController.navigate(call)
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
}