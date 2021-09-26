package com.example.tashxis.business.util
import io.paperdb.Paper
object Language {
    fun saveLanguage(language: String,position:Int) {
        Paper.book().write("language", language)
        Paper.book().write("position",position)
    }
    fun getLanguage(): String {
        return Paper.book().read("language","ru")
    }
}
object LanguagePosition{

    fun getLanguagePositon():Int
    {
        return  Paper.book().read("position",0)
    }
}
object RegionPosition{
    fun saveRegionLoginPosition(id:Int,position:Int,name:String){
        Paper.book().write("regionid",id)
        Paper.book().write("regionposition",position)
        Paper.book().write("regionname",name)


    }
    fun getRegionPositon():Int
    {
        return  Paper.book().read("regionposition",0)
    }
    fun getRegion():Int{
        return Paper.book().read("regionid",2)
    }
    fun getRegionName():String
    {
        return Paper.book().read("regionname","г.Ташкент")
    }

}


