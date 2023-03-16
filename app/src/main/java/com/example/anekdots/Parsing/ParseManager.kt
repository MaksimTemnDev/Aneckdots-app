package com.example.anekdots.Parsing

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException

class ParseManager {
    val url = "https://www.anekdot.ru"
    lateinit var listAnekdots: List<Elements>
    var lastLink = "url"
    var hasNextPage = true
    fun  listOfTags(): ArrayList<String>{
        val tagPage = "https://www.anekdot.ru/tags/"
        try {
            val doc = Jsoup.connect(tagPage).get()
            val tagsList = ArrayList<String>()
            val list = doc.select("div.wrapper.desktop").select("div.content.content-min")
                .select("div.col-left.col-left-margin").select("div.tablebox")
                .select("div.tags-cloud").select("a")
            listAnekdots = list as List<Elements>
            for (i in 0 until list.size) {
                tagsList.add(list[i].text())
            }
            return tagsList
        }catch (e: IOException){
            e.printStackTrace()
        }
        return ArrayList()
    }

    fun getAneckdotsList(indx: Int?, searchNext: Boolean): ArrayList<Aneckdot> {
        val aneckdots = ArrayList<Aneckdot>()
        var limit = indx
        var nSearch = searchNext
        var newPage = (listAnekdots.get(limit!!) as Element).attr("href")
        limit = 0
        var request = "$url$newPage"
        if(lastLink.contains("tags") && searchNext == true){
            if(!lastLink.contains("http")) {
                request = "$url$lastLink"
            }else{
                request = "$lastLink"
            }
        }
        var page = Jsoup.connect(request).get()
        lastLink = request
        //val page = Jsoup.connect("$url$newPage").get()
        var goNext = true
        while (limit < 3 && goNext == true) {
            if(nSearch == false) {
                var listAnekd =
                    page.body().select("div.wrapper.desktop").select("div.content.content-min")
                        .select("div.col-left.col-left-margin").select("div.topicbox").toList()
                listAnekd = listAnekd.subList(1, listAnekd.lastIndex + 1)
                limit++
                for (element in listAnekd) {
                    val str = moduleTextToString(element.select("div.text"))
                    val info = element.select("div.votingbox").select("div.btn2").select("a")
                    if (info.size == 3) {
                        if (info[1].attr("href").toString() != "#") {
                            aneckdots.add(Aneckdot(str, info[1].attr("href")))
                        } else {
                            aneckdots.add(Aneckdot(str, info[2].attr("href")))
                        }
                    } else if (info.size == 2) {
                        if (info[1].hasAttr("data-site")) {
                            aneckdots.add(Aneckdot(str, info[1].attr("data-site")))
                        } else {
                            aneckdots.add(Aneckdot(str, url + info[1].attr("href")))
                        }
                    } else if (info.size > 3) {
                        aneckdots.add(Aneckdot(str, info[2].attr("href")))
                    }
                }
            }
            var nextPageLink =
                page.body().select("div.wrapper.desktop").select("div.content.content-min")
                    .select("div.col-left.col-left-margin").select("div.pageslist").select("a")
            if(nextPageLink.size>0) {
                val mean = nextPageLink.get(nextPageLink.lastIndex).text()
                var priorityLink = nextPageLink.get(nextPageLink.lastIndex).attr("href")
                if (priorityLink != null && mean.contains("след")) {
                    page = Jsoup.connect("$url${priorityLink}").get()
                    lastLink = priorityLink
                    nSearch = false
                } else {
                    goNext = false
                }
            }else{
                goNext = false
            }
            hasNextPage = goNext
        }
        return aneckdots
    }

    fun randomAneckdotsList(): ArrayList<Aneckdot> {
        val aneckdotsList = ArrayList<Aneckdot>()
        val doc = Jsoup.connect("https://www.anekdot.ru/random/anekdot/").get()
        val listAll = doc.body().selectFirst("div.wrapper.desktop").selectFirst("div.content.content-min")
            .select("div.col-left.col-left-margin").select("div.topicbox").toList()
        for (i in 1 until listAll.size) {
            val currentElement = listAll.get(i)
            val text =
                currentElement.select("p.title").text() + moduleTextToString(currentElement.select("div.text"))
            if(text != ""){
                val info = currentElement.select("div.votingbox").select("div.btn2").select("a")
                val link = findAuthorLink(info)
                aneckdotsList.add(Aneckdot(text, link))
            }
        }
        return aneckdotsList
    }

    fun findAuthorLink(info: Elements): String {
        if (info.size == 3) {
            if (info[1].attr("href").toString() != "#") {
                return info[1].attr("href")
            } else {
                return url + info[2].attr("href")
            }
        } else if (info.size == 2) {
            if (info[1].hasAttr("data-site")) {
                return info[1].attr("data-site")
            } else {
                return url + info[1].attr("href")
            }
            println("Источник")
        } else if (info.size > 3) {
            return info[2].attr("href")
        }
        return ""
    }

    fun canMoveToNext(): Boolean{
        val res = hasNextPage
        return res
    }

        fun moduleTextToString(input: Elements): String{
            val res = input.toString().replace("<br>", "").replace("</div>", "").replaceBefore(">\n", "").replace('>', ' ').replace('#', ' ')
            return res
        }
}