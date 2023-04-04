package com.example.anekdots

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.anekdots.Parsing.Aneckdot
import com.example.anekdots.Parsing.ParseManager
import com.example.anekdots.Room.Repository.Database
import com.example.anekdots.ui.theme.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    lateinit var listOfArticles: ArrayList<String>
    lateinit var listOfAneckdots: ArrayList<Aneckdot>
    val parser = ParseManager()
    var curScreen = Screen.StartScreen.route
    var thread2 = Thread()
    var nconnect = false
    lateinit var dbOutputList: List<com.example.anekdots.Room.Entites.Aneckdot>
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        intit(parser = parser)
        var canShowFavorite = false;
        listOfAneckdots = ArrayList<Aneckdot>()
        listOfAneckdots.add(Aneckdot("txt", "link"))
        nconnect = isOnline(applicationContext)
        setContent {
            MaterialTheme {
                val navContrller = rememberNavController()
                val window = this.window
                window.statusBarColor = ContextCompat.getColor(this, R.color.toolBar)
                NavHost(navController = navContrller, startDestination = Screen.StartScreen.route) {
                    navController = navContrller
                    composable(route = Screen.MenuScreen.route) {
                        if(nconnect == true) {
                            curScreen = Screen.MenuScreen.route
                            menuBuild(cathegory = listOfArticles, navContrller)
                        }
                    }
                    composable(route = Screen.AnekdotScreen.route + "/{id}", arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                            defaultValue = 0
                            nullable = false
                        }
                    )) { input ->
                        var indx = input.arguments?.getInt("id")
                        curScreen = Screen.AnekdotScreen.route
                        val db = Database.getDatabase(applicationContext)
                        var isFavourite = false
                        if(indx == -2){
                            canShowFavorite = true
                        }
                        if(nconnect == true || canShowFavorite == true) {
                            if (indx == -1) {
                                lifecycleScope.launch {
                                    loadRandomAneckd(false)
                                }
                            } else if (indx == -2) {
                                readDb(db)
                                isFavourite = true
                                canShowFavorite = false
                            } else if (indx == -3) {
                                loadBestAnrckd()
                            }
                            anekdotScreen(texts = listOfAneckdots, indx = indx, db, isFavourite)
                        }
                    }
                    composable(route = Screen.StartScreen.route){
                        curScreen = Screen.StartScreen.route
                        start_sc(navHostController = navController, applicationContext)
                    }
                }
            }
            if(nconnect == false && curScreen != Screen.StartScreen.route && canShowFavorite == false){
                noConnectDialog(
                    title = "Ой, что-то не то!",
                    desc = "Не установленно соединение с сетью." + "\n"
                            + "Убедитесь что устройство подключено к сети интернет, после чего попробуйте снова.",
                    onDismiss = {
                        nconnect = true
                    }
                )
                navController.navigate(Screen.MenuScreen.route)
            }
        }
    }

    fun loadRandomAneckd(add: Boolean) {
        thread {
            if (add == false) {
                listOfAneckdots = parser.randomAneckdotsList()
            }else{

            }
        }
    }

    //load aneckdots to listOfAneckdots
    fun loadBestAnrckd(){
        thread {
            listOfAneckdots = parser.loadBestFromPast()
        }
    }

    fun intit(parser: ParseManager) = runBlocking{
        val runnable = Runnable{
            listOfArticles = parser.listOfTags().clone() as ArrayList<String>
        }
        launch {
            thread2 = Thread(runnable)
            thread2.start()
        }
    }

    fun listComplite(
        parser: ParseManager,
        indx: Int?,
        navHostController: NavHostController
    ){
        val call = "${Screen.AnekdotScreen.route}/" + indx
        this.nconnect = isOnline(applicationContext)
        if(nconnect == true) {
            thread {
                listOfAneckdots = parser.getAneckdotsList(indx, false)
                runOnUiThread {
                    navHostController.navigate(route = call)
                }
            }
        }else{

        }
    }

    fun listadd(
        parser: ParseManager,
        indx: Int?,
        showenId: MutableState<Int>
    ){
        thread {
            val bufListToAdd = parser.getAneckdotsList(indx, true)
            if (bufListToAdd.size>0) {
                listOfAneckdots.addAll(bufListToAdd)
                showenId.value++
            }
        }
    }

    @Preview
    @Composable
    fun show_start() {
        start_sc(navController, applicationContext)
    }


    @Composable
    fun menuBuild(
        cathegory: List<String>,
        navHostController: NavHostController
    ){
        Box(modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BeidgeMiddle,
                        BeidgeMain,
                        Brown
                    )
                )
            )) {
            LazyColumn(
                modifier = Modifier.padding(vertical = 12.dp)
                    , verticalArrangement = Arrangement.SpaceBetween
            ) {
                itemsIndexed(cathegory) { index, item ->
                    menuElement(cathegory = item, index, navHostController)
                }
            }
        }
    }

    @Composable
    fun menuElement(
        cathegory: String,
        index: Int,
        navHostController: NavHostController
    ){
        var showDialog = remember {
            mutableStateOf(false)
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 22.dp, end = 22.dp, bottom = 10.dp)
            .clickable {
                nconnect = isOnline(applicationContext)
                if (nconnect) {
                    lifecycleScope.launch {
                        listComplite(parser, index, navHostController)
                    }
                } else {
                    showDialog.value = true
                }
            },
            shape = RoundedCornerShape(22.dp),
            backgroundColor = BeidgeWhite,
            elevation = 2.dp) {
            Column(modifier = Modifier.padding(bottom = 2.dp, top = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier,
                contentAlignment = Alignment.Center
                ) {
                    Text(text = cathegory,
                        style = TextStyle(
                        color = BrownMain,
                        fontSize = 26.sp,
                        shadow = Shadow(
                            color = LightBeidge,
                            offset = Offset(1f, 1f),
                            blurRadius = 8f)
                        ),

                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        if(showDialog.value){
            noConnectDialog(
                title = "Ой, что-то не то!",
                desc = "Не установленно соединение с сетью." + "\n"
                        + "Убедитесь что устройство подключено к сети интернет, после чего попробуйте снова.",
                onDismiss = {
                    showDialog.value = false;
                }
            )
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun anekdotScreen(texts: List<Aneckdot>, indx: Int?, db: Database, isFavourite: Boolean){
        var showenId = remember {
            mutableStateOf(0)
        }
        val scaffoldState= rememberScaffoldState()
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .background(BeidgeMiddle),
            scaffoldState = scaffoldState,
            snackbarHost = {
                SnackbarHost(hostState = it){
                    data->
                    Snackbar(snackbarData = data,
                        backgroundColor = BrownMain,
                        contentColor = BeidgeWhite
                    )
                }
            }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BeidgeMiddle)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textView(texts = texts, showenId, indx, db, isFavourite, scaffoldState)
                }
            }
        }
    }

    @Composable
    fun textView(
        texts: List<Aneckdot>,
        showenId: MutableState<Int>,
        indx: Int?,
        db: Database,
        isFavourite: Boolean,
        scaffoldState: ScaffoldState
    ){
        val showenText = remember {
            mutableStateOf(listOfAneckdots)
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 52.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BeidgeWhite),
            contentAlignment = Alignment.CenterStart) {
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
                Box(modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .padding(top = 15.dp, bottom = 8.dp, start = 10.dp, end = 10.dp)
                    .verticalScroll(rememberScrollState())) {
                    Text(text =
                    if(indx!! >-1){
                        showenText.value.get(showenId.value).text
                    }else{
                        listOfAneckdots.get(showenId.value).text
                         },
                        color = BrownDark, fontSize = 18.sp)
                    //Text(text = texts.get(showenId.value).text, color = BrownDark, fontSize = 18.sp)
                }
                val fv = isFavourite
                bottomNavi(showenId, indx, db, isFavourite, scaffoldState)
                //bottomNavi(showenId, showenText.value.size, showenText.value.get(showenId.value).text, showenText.value.get(showenId.value).link, indx)
            }
        }
    }

    override fun onBackPressed() {
        navController.navigate("start_sc")
    }

    private fun insertToDB(aneckdot: Aneckdot, db: Database){
        thread {
            val currentAneckdot = com.example.anekdots.Room.Entites.Aneckdot(null, aneckdot.text, aneckdot.link)
            db.aneckdotDao().addAneckdot(currentAneckdot)
        }
    }

    private fun readDb(db: Database) {
        db.aneckdotDao().readAll().observe(this){list->
            var lstAneckd = listOfAneckdots
            listOfAneckdots.clear()
            if(list.size>0){
                var curSize = listOfAneckdots.size
                curSize = listOfAneckdots.size
                dbOutputList = list
                list.forEach {
                    listOfAneckdots.add(Aneckdot(it.text, it.link))
                }
            }else{
                listOfAneckdots.add(Aneckdot("some text", "link"))
            }
        }
    }

    private fun findInDbList(aneckdot: Aneckdot): com.example.anekdots.Room.Entites.Aneckdot{
        for (i in 0 until dbOutputList.size){
            if(dbOutputList[i].text == aneckdot.text){
                return dbOutputList[i]
            }
        }
        return com.example.anekdots.Room.Entites.Aneckdot(null, "not found", "not found")
    }

    private fun dropFromDb(db: Database, element: com.example.anekdots.Room.Entites.Aneckdot){
        thread {
            db.aneckdotDao().deleteAneckdot(element)
        }
    }


    @Composable
    fun bottomNavi(
        showenId: MutableState<Int>,
        indx: Int?,
        db: Database,
        isFavourite: Boolean,
        scaffoldState: ScaffoldState
    ) {
        val nextTapBool = remember {
            mutableStateOf(true)
        }
        val inConect = remember {
            mutableStateOf(false)
        }
        val isConnect = isOnline(applicationContext)
        val coroutineScope = rememberCoroutineScope()
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)){
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround)
            {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BrownMain)
                            .padding(5.dp)
                            .clickable {
                                if (showenId.value > 0) {
                                    showenId.value--
                                }
                            }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Back",
                            tint = BeidgeMiddle,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BrownMain)
                            .padding(5.dp)
                            .clickable {
                                val clipboardManager =
                                    getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                val clipData = ClipData.newPlainText(
                                    "aneckdot", listOfAneckdots.get(
                                        showenId.value
                                    ).text
                                )
                                clipboardManager.setPrimaryClip(clipData)
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        "Text copied!",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.content_copy),
                            contentDescription = "Favorite",
                            tint = BeidgeMiddle,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BrownMain)
                            .padding(5.dp)
                            .clickable {
                                if (isFavourite) {
                                    dropFromDb(
                                        db,
                                        findInDbList(listOfAneckdots.get(showenId.value))
                                    )
                                    if (showenId.value >= 1) {
                                        readDb(db)
                                        showenId.value--
                                    } else if (listOfAneckdots.size > 1) {
                                        readDb(db)
                                        if (listOfAneckdots.size > 1 && showenId.value < listOfAneckdots.size - 1) {
                                            showenId.value++
                                        } else {
                                            showenId.value = dbOutputList.size - 1
                                        }
                                    } else {
                                        readDb(db)
                                        navController.navigate("start_sc")
                                    }
                                } else {
                                    insertToDB(listOfAneckdots.get(showenId.value), db)
                                }
                            }) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavourite) {
                                    R.drawable.delete_24
                                } else {
                                    R.drawable.favorite
                                }
                            ),
                            contentDescription = "Favorite",
                            tint = BeidgeMiddle,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BrownMain)
                            .padding(5.dp)
                            .clickable {
                                try {
                                    if (listOfAneckdots.get(indx!!).link.contains("http") && isConnect) {
                                        val browse = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(listOfAneckdots.get(indx!!).link)
                                        )
                                        startActivity(browse)
                                    } else {
                                        inConect.value = isConnect
                                    }
                                } catch (e: IOException) {
                                    Toast
                                        .makeText(
                                            getApplicationContext(),
                                            "Link was incorrect!",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }

                            }) {
                        Icon(
                            painter = painterResource(id = R.drawable.author),
                            contentDescription = "Favorite",
                            tint = BeidgeMiddle,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(BrownMain)
                            .padding(5.dp)
                            .clickable(enabled = nextTapBool.value) {
                                if (showenId.value + 1 < listOfAneckdots.size) {
                                    showenId.value++
                                    if (showenId.value == listOfAneckdots.size - 3) {
                                        lifecycleScope.launch {
                                            if (indx != null) {
                                                if (indx <= -1) {
                                                    Log.d(
                                                        "tag",
                                                        "current size anekdots is ${listOfAneckdots.size.toString()}"
                                                    )
                                                } else {
                                                    if (parser.canMoveToNext()) {
                                                        listadd(parser, indx, showenId)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else if (showenId.value < listOfAneckdots.size) {
                                    nextTapBool.value = true
                                }
                            }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_forward),
                            contentDescription = "Next",
                            tint = BeidgeMiddle,
                            modifier = Modifier.size(28.dp)
                        )
                    }
            }
        }
        if(inConect.value == true){
            noConnectDialog(
                title = "Ой, что-то не то!",
                desc = "Не установленно соединение с сетью." + "\n"
                        + "Убедитесь что устройство подключено к сети интернет, после чего попробуйте снова.",
                onDismiss = {
                    inConect.value = false
                }
            )
        }
    }
}