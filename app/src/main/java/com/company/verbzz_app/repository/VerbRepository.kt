package com.company.verbzz_app.repository

import android.util.Log
import com.company.verbzz_app.data.DataOrException
import com.company.verbzz_app.model.englishModel.EnglishModel
import com.company.verbzz_app.model.frenchModel.FrenchModel
import com.company.verbzz_app.network.LanguagesApi
import javax.inject.Inject

class VerbRepository @Inject constructor(private val api: LanguagesApi) {
    /* indexes created to match the first letter of the verb sought for with the database
    so the function looks for a range and not the entire database. 26 indexes represent a-z */
    private val frenchStart = intArrayOf(0, 898, 1477, 2561, 4076, 5288, 5668, 5991, 6146, 6433, 6507, 6520, 6702, 7155, 7271, 7395, 8182, 8207, 9528, 10184, 10650, 10672, 10865, 10866, 10868, 10875)
    private val frenchEnd = intArrayOf(897, 1476, 2560, 4075, 5287, 5667, 5990, 6145, 6432, 6506, 6519, 6701, 7154, 7270, 7394, 8181, 8206, 9527, 10183, 10649, 10671, 10864, 10865, 10867, 10874, 10895)
    private val englishStart = intArrayOf(0, 45, 124, 212, 271, 313, 358, 389, 426, 461, 474, 483, 515, 551, 561, 577, 655, 660, 726, 882, 938, 953, 963, 1003, 1004, 1010)
    private val englishEnd = intArrayOf(44, 123, 211, 270, 312, 357, 388, 425, 460, 473, 482, 514, 550, 560, 576, 654, 659, 725, 881, 937, 952, 962, 1002, 1003, 1009, 1010)

    suspend fun getEnglishVerbData() : DataOrException<EnglishModel, Boolean, Exception> {
        val response = try {
            api.getEnglishVerbs()
        } catch (ex: Exception) {
            Log.e("ENGLISH_DATA_ERROR", "English Data was not obtained")
            return DataOrException(e = ex)
        }
        return DataOrException(data = response)
    }

    fun returnEnglishVerbPosition(data: EnglishModel, verb: String) : Int {
        val index = (verb.lowercase()[0]).code - 97
        var position = -1
        for(i in englishStart[index]..englishEnd[index]) {
            if(data[i].infinitive[0] == verb) {
                position = i
                break
            }
        }
        return position
    }

    suspend fun getFrenchVerbData() : DataOrException<FrenchModel, Boolean, Exception>{
        val response = try {
            api.getFrenchVerbs()
        } catch (ex: Exception) {
            Log.e("FRENCH_DATA_ERROR", "French Data was not obtained")
            return DataOrException(e = ex)
        }
        return DataOrException(data = response)
    }

    fun returnFrenchVerbPosition(data: FrenchModel, verb: String) : Int {
        var index = (verb.lowercase()[0]).code - 97
        var position = -1
        //14 = function will look for the range of verbs starting with "o", where ôter is
        if(verb == "ôter") index = 14
        /*4 = function will look for the range of verbs starting with "e",
        to account for verbs starting with accented "e"s*/
        else if(index > 25) index = 4

        for(i in frenchStart[index]..frenchEnd[index]) {
            if(data[i].infinitif.présent[0] == verb) {
                position = i
                break
            }
        }
        return position
    }
}