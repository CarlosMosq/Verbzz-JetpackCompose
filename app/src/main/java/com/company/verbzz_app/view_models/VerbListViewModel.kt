package com.company.verbzz_app.view_models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.verbzz_app.R
import com.company.verbzz_app.data.DataOrException
import com.company.verbzz_app.model.englishModel.EnglishModelItem
import com.company.verbzz_app.model.frenchModel.FrenchModelItem
import com.company.verbzz_app.repository.VerbRepository
import com.company.verbzz_app.utils.RandomizeVerbsAndTenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerbListViewModel @Inject constructor(private val verbRepository: VerbRepository)
    : ViewModel() {
    private var englishData: DataOrException<EnglishModelItem, Boolean, Exception>
    = DataOrException(null, false, null)
    private var frenchData: DataOrException<FrenchModelItem, Boolean, Exception>
    = DataOrException(null, false, null)
    var moodList: DataOrException<List<List<String>>, Boolean, Exception>
    = DataOrException(null, false, null)
    var tenseList: DataOrException<List<String>, Boolean, Exception>
            = DataOrException(null, false, null)

    var pronoun: MutableState<String> = mutableStateOf("")
    var verb: MutableState<String> = mutableStateOf("")
    var pronounIndex: MutableState<Int> = mutableStateOf(0)
    private var verbIndex: MutableState<Int> = mutableStateOf(0)

    fun getVerbData(
        language: String,
        verbText: MutableState<String>,
        context: Context
    ) = viewModelScope.launch {
        try {
            if(language == "English") {
                englishData.loading = true
                val englishList = verbRepository.getEnglishVerbData()
                val index = verbRepository.returnEnglishVerbPosition(englishList.data!!, verbText.value)
                if(index >= 0) englishData.data = englishList.data!![index]
                else Toast.makeText(context, R.string.verbNotFound, Toast.LENGTH_SHORT).show()
                if(englishData.data != null) englishData.loading = false
                println(englishData.data.toString())
            } else {
                frenchData.loading = true
                val frenchList = verbRepository.getFrenchVerbData()
                val index = verbRepository.returnFrenchVerbPosition(frenchList.data!!, verbText.value)
                if(index >= 0) frenchData.data = frenchList.data!![index]
                else Toast.makeText(context, R.string.verbNotFound, Toast.LENGTH_SHORT).show()
                if(frenchData.data != null) frenchData.loading = false
                println(frenchData.data.toString())
            }
        } catch (ex: Exception) {
            if(language == "English") {
                englishData.e = ex
                Log.e("GetVerbData", ex.message!!)
            } else {
                frenchData.e = ex
                Log.e("GetVerbData", ex.message!!)
            }
        }
    }

    fun getTenseData(
        language: String,
        tense: String) : List<String> {
        try {
            return if(language == "English") {
                returnVerbListEnglish(model = englishData.data!!, tense = tense)
            } else {
                returnVerbListFrench(model = frenchData.data!!, tense = tense)
            }
        } catch (ex: Exception) {
            Log.e("GetTenseData", ex.message!!)

        }
        return listOf()
    }

    fun getMoodData(mood: String) = viewModelScope.launch {
        try {
            moodList.data = when(mood) {
                "Indicative" -> listOf(
                    englishData.data!!.indicative.present,
                    englishData.data!!.indicative.imperfect,
                    englishData.data!!.indicative.future,
                    englishData.data!!.indicative.perfect,
                    englishData.data!!.indicative.plusperfect,
                    englishData.data!!.indicative.previousFuture,
                )
                "Conditional" -> listOf(
                    englishData.data!!.conditional.conditional,
                    englishData.data!!.conditional.conditionalPerfect
                )
                "Subjunctive" -> listOf(
                    englishData.data!!.subjuntive.present,
                    englishData.data!!.subjuntive.perfect
                )
                "Imperative", "Participle", "Gerund", "Infinitive" ->
                    listOf(
                        englishData.data!!.imperative,
                        englishData.data!!.participle,
                        englishData.data!!.gerund,
                        englishData.data!!.infinitive
                    )
                "Indicatif" -> listOf(
                    frenchData.data!!.indicatif.présent,
                    frenchData.data!!.indicatif.passéComposé,
                    frenchData.data!!.indicatif.imparfait,
                    frenchData.data!!.indicatif.plusQueParfait,
                    frenchData.data!!.indicatif.passéSimple,
                    frenchData.data!!.indicatif.passéAntérieur,
                    frenchData.data!!.indicatif.futurSimple,
                    frenchData.data!!.indicatif.futurAntérieur
                )
                "Conditionnel" -> listOf(
                    frenchData.data!!.conditionnel.présent,
                    frenchData.data!!.conditionnel.passé1èreForme,
                    frenchData.data!!.conditionnel.passé2èmeForme,
                )
                "Subjonctif" -> listOf(
                    frenchData.data!!.subjonctif.présent,
                    frenchData.data!!.subjonctif.passé,
                    frenchData.data!!.subjonctif.imparfait,
                    frenchData.data!!.subjonctif.plusQueParfait,
                )
                "Impératif", "Participe", "Infinitif" ->
                    listOf(
                        frenchData.data!!.impératif.présent,
                        frenchData.data!!.impératif.passé,
                        frenchData.data!!.participe.présent,
                        frenchData.data!!.participe.passé,
                        frenchData.data!!.infinitif.présent,
                        frenchData.data!!.infinitif.passé
                    )
                else -> listOf(listOf(""))
            }

        } catch (ex: Exception) {
            Log.e("GetMoodData", ex.message!!)
        }
    }

    fun getTenseTitle(mood: String) = viewModelScope.launch {
        try {
            tenseList.data = when(mood) {
                "Indicative" -> listOf(
                    "Present", "Past", "Future", "Present Perfect", "Past Perfect", "Future Perfect"
                )
                "Conditional" -> listOf("Present Conditional", "Perfect Conditional")
                "Subjunctive" -> listOf("Present Subjunctive", "Perfect Subjunctive")
                "Imperative", "Participle", "Gerund", "Infinitive" ->
                    listOf("Imperative", "Participle", "Gerund", "Infinitive")
                "Indicatif" -> listOf(
                    "Présent", "Passé Composé", "Imparfait", "Plus-Que-Parfait", "Passé Simple",
                    "Passé Antérieur", "Futur Simple", "Futur Antérieur"
                )
                "Conditionnel" -> listOf(
                    "Présent Conditionnel", "Passé première forme", "Passé deuxième forme"
                )
                "Subjonctif" -> listOf(
                    "Présent Subjonctif","Passé Subjonctif", "Imparfait Subjonctif",
                    "Plus-que-parfait Subjonctif"
                )
                "Impératif", "Participe", "Infinitif" ->
                    listOf(
                        "Impératif Présent", "Impératif Passé", "Participe Présent",
                        "Participe Passé", "Infinitif Présent", "Infinitif Passé"
                    )
                else -> listOf("")
            }

        } catch (ex: Exception) {
            Log.e("GetMoodData", ex.message!!)
        }
    }

    fun setPronounAndVerb(
        verbCount: String,
        tense: String,
        language: String,
        randomizeVerbsAndTenses: RandomizeVerbsAndTenses,
        context: Context
    ) = viewModelScope.launch {
        try {
            //there are 6 pronouns, so index can be from 0 to 5 and here a random index is returned
            pronounIndex.value = randomizeVerbsAndTenses.returnRandomIndex(5)
            //sets a random number within the range of verbs available for a specific tense list;
            verbIndex.value = randomizeVerbsAndTenses.returnRandomIndex(returnMin(
                nbr = verbCount.toInt(),
                tense = tense,
                language = language,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses))

            if(language == context.getString(R.string.english)) {
                pronoun.value = returnPronounListEnglish(
                    transformEnglishTense(tense))[pronounIndex.value]
                verb.value = if(tense == "Past (irr)") {
                    randomizeVerbsAndTenses.getFiftyIrregularVerbsEnglish()[verbIndex.value]
                } else {
                    randomizeVerbsAndTenses.getHundredMostCommonEnglish()[verbIndex.value]
                }
            } else {
                verb.value =
                    when(tense) {
                        "Présent (-er)" -> randomizeVerbsAndTenses.getMostCommonErFrench()
                        "Présent (-ir)" -> randomizeVerbsAndTenses.getMostCommonIrFrench()
                        "Présent (-re)" -> randomizeVerbsAndTenses.getMostCommonReFrench()
                        "Présent (Réguliers)" -> randomizeVerbsAndTenses.getAllRegularsFrench()
                        "Présent (Irréguliers)" -> randomizeVerbsAndTenses.getMostCommonIrregularsFrench()
                        "Présent (Réfléchis)", "Passé Composé (Réfléchis)" ->
                            randomizeVerbsAndTenses.getMostCommonReflexiveFrench()
                        "Passé Composé (avoir)" -> randomizeVerbsAndTenses.getMostCommonAvoir()
                        "Passé Composé (être)" -> randomizeVerbsAndTenses.getMostCommonEtre()
                        else -> randomizeVerbsAndTenses.getHundredMostCommonFrench()
                    }[verbIndex.value]
                pronoun.value = returnPronounListFrench(
                    transformFrenchTense(tense),
                    verb.value)[pronounIndex.value]
            }

        } catch (ex: Exception) {
            Log.e("SetPronounAndVerb", ex.message!!)
        }
        getVerbData(language = language, verbText = verb, context = context)
    }

    private fun returnVerbListEnglish(
        model: EnglishModelItem,
        tense: String
    ) : List<String> {
        return when(transformEnglishTense(tense)) {
                //I work
                "Present" -> model.indicative.present
                //I worked
                "Past" -> model.indicative.imperfect
                //I will work
                "Future" -> model.indicative.future
                //I have worked
                "Present Perfect" -> model.indicative.perfect
                //I had worked
                "Past Perfect" -> model.indicative.plusperfect
                //I will have worked
                "Future Perfect" -> model.indicative.previousFuture
                //I would work
                "Present Conditional" -> model.conditional.conditional
                //I would have worked
                "Perfect Conditional" -> model.conditional.conditionalPerfect
                //that I work
                "Present Subjunctive" -> model.subjuntive.present
                //I may have worked
                "Perfect Subjunctive" -> model.subjuntive.perfect
                else -> listOf()
            }
        }

    private fun returnVerbListFrench(
        model: FrenchModelItem,
        tense: String
    ) : List<String> {
        return when(transformFrenchTense(tense)) {
            //Je travaille
            "Présent" -> model.indicatif.présent
            //J'ai travaillé
            "Passé Composé" -> model.indicatif.passéComposé
            //Je travaillais
            "Imparfait" -> model.indicatif.imparfait
            //J'avais travaillé
            "Plus-Que-Parfait" -> model.indicatif.plusQueParfait
            //Je travaillai
            "Passé Simple" -> model.indicatif.passéSimple
            //J'eus travaillé
            "Passé Antérieur" -> model.indicatif.passéAntérieur
            //Je travaillerai
            "Futur Simple" -> model.indicatif.futurSimple
            //J'aurai travaillé
            "Futur Antérieur" -> model.indicatif.futurAntérieur
            //Je travaillerais
            "Présent Conditionnel" -> model.conditionnel.présent
            //J'aurais travaillé
            "Passé première forme" -> model.conditionnel.passé1èreForme
            //J'eusse travaillé
            "Passé deuxième forme" -> model.conditionnel.passé2èmeForme
            //que je travaille
            "Présent Subjonctif" -> model.subjonctif.présent
            //que j'aie travaillé
            "Passé Subjonctif" -> model.subjonctif.passé
            //que je travaillasse
            "Imparfait Subjonctif" -> model.subjonctif.imparfait
            //que j'eusse travaillé
            "Plus-que-parfait Subjonctif" -> model.subjonctif.plusQueParfait
            else -> listOf()
        }
    }

    //Returns pronouns list that applies to different french verb tenses;
    private fun returnPronounListFrench(tense: String, verb: String): Array<String> {
        if (returnAuxiliary(verb) == "Avoir") {
            when (tense) {
                "Présent", "Imparfait", "Passé Simple", "Futur Simple", "Présent Conditionnel" ->
                    return arrayOf(
                    "Je ",
                    "Tu ",
                    "Il/elle/on ",
                    "Nous ",
                    "Vous ",
                    "Ils/elles "
                )
                "Passé Composé" -> return arrayOf(
                    "J'ai ",
                    "Tu as ",
                    "Il/elle/on a ",
                    "Nous avons ",
                    "Vous avez ",
                    "Ils/elles ont "
                )
                "Plus-Que-Parfait" -> return arrayOf(
                    "J'avais ",
                    "Tu avais ",
                    "Il/elle/on avait ",
                    "Nous avions ",
                    "Vous aviez ",
                    "Ils/elles avaient "
                )
                "Passé Antérieur" -> return arrayOf(
                    "J'eus ",
                    "Tu eus ",
                    "Il/elle/on eut ",
                    "Nous eûmes ",
                    "Vous eûtes ",
                    "Ils/elles eurent "
                )
                "Futur Antérieur" -> return arrayOf(
                    "J'aurais ",
                    "Tu auras ",
                    "Il/elle/on aura ",
                    "Nous aurons ",
                    "Vous aurez ",
                    "Ils/elles auront "
                )
                "Passé première forme" -> return arrayOf(
                    "J'aurais ",
                    "Tu auras ",
                    "Il/elle/on aura ",
                    "Nous aurions ",
                    "Vous auriez ",
                    "Ils/elles auraient "
                )
                "Passé deuxième forme" -> return arrayOf(
                    "J'eusse ",
                    "Tu eusses ",
                    "Il/elle/on eût ",
                    "Nous eussions ",
                    "Vous eussiez ",
                    "Ils/elles eussent "
                )
                "Passé Subjonctif" -> return arrayOf(
                    "que j'aie ",
                    "que tu as ",
                    "qu'il/elle/on ait ",
                    "que nous ayons ",
                    "que vous ayez ",
                    "Ils/elles aient "
                )
                "Présent Subjonctif", "Imparfait Subjonctif" -> return arrayOf(
                    "que je ",
                    "que tu ",
                    "qu'il/elle/on ",
                    "que nous ",
                    "que vous ",
                    "qu'ils/elles "
                )
                "Plus-que-parfait Subjonctif" -> return arrayOf(
                    "que j'eusse ",
                    "que tu eusses ",
                    "qu'il/elle/on eût ",
                    "que nous eussions ",
                    "que vous eussiez ",
                    "qu'ils/elles eussent "
                )
            }
        } else {
            when (tense) {
                "Présent", "Imparfait", "Passé Simple", "Futur Simple", "Présent Conditionnel" -> return arrayOf(
                    "Je ",
                    "Tu ",
                    "Il/elle/on ",
                    "Nous ",
                    "Vous ",
                    "Ils/elles "
                )
                "Passé Composé" -> return arrayOf(
                    "Je suis ",
                    "Tu es ",
                    "Il/elle/on est ",
                    "Nous sommes ",
                    "Vous êtes ",
                    "Ils/elles sont "
                )
                "Plus-Que-Parfait" -> return arrayOf(
                    "J'étais ",
                    "Tu étais ",
                    "Il/elle/on était ",
                    "Nous étions ",
                    "Vous étiez ",
                    "Ils/elles étaient "
                )
                "Passé Antérieur" -> return arrayOf(
                    "Je fus ",
                    "Tu fus ",
                    "Il/elle/on fut ",
                    "Nous fûmes ",
                    "Vous fûtes ",
                    "Ils/elles furent "
                )
                "Futur Antérieur" -> return arrayOf(
                    "Je serai ",
                    "Tu seras ",
                    "Il/elle/on sera ",
                    "Nous serons ",
                    "Vous serez ",
                    "Ils/elles seront "
                )
                "Passé première forme" -> return arrayOf(
                    "Je serais ",
                    "Tu serais ",
                    "Il/elle/on serait ",
                    "Nous serions ",
                    "Vous seriez ",
                    "Ils/elles seraient "
                )
                "Passé deuxième forme" -> return arrayOf(
                    "Je fusse ",
                    "Tu fusses ",
                    "Il/elle/on fût ",
                    "Nous fussions ",
                    "Vous fussiez ",
                    "Ils/elles fussent "
                )
                "Passé Subjonctif" -> return arrayOf(
                    "que je sois ",
                    "que tu sois ",
                    "qu'il/elle/on soit ",
                    "que nous soyons ",
                    "que vous soyez ",
                    "Ils/elles soient "
                )
                "Présent Subjonctif", "Imparfait Subjonctif" -> return arrayOf(
                    "que je ",
                    "que tu ",
                    "qu'il/elle/on ",
                    "que nous ",
                    "que vous ",
                    "qu'ils/elles "
                )
                "Plus-que-parfait Subjonctif" -> return arrayOf(
                    "que je fusse ",
                    "que tu fusses ",
                    "qu'il/elle/on fût ",
                    "que nous fussions ",
                    "que vous fussiez ",
                    "qu'ils/elles fussent "
                )
            }
        }
        return arrayOf("")
    }

    private fun returnAuxiliary(verb: String): String {
        return when (verb) {
            "aller", "arriver", "descendre", "redescendre", "entrer", "rentrer", "monter",
            "remonter", "mourir", "naître", "renaître", "partir", "repartir", "passer",
            "rester", "retourner", "sortir", "ressortir", "tomber", "retomber", "venir",
            "devenir", "parvenir", "revenir" -> "Etre"
            else -> "Avoir"
        }
    }

    private fun returnPronounListEnglish(tense: String): Array<String> {
        when (tense) {
            //I work
            //I worked
            "Present", "Past" -> return arrayOf("I ", "You ", "He/she/it ", "We ", "You ", "They ")
            //I will work
            "Future" -> return arrayOf("I will ", "You will ", "He/she/it will ", "We will ", "You will ", "They will ")
            //I have worked
            "Present Perfect" -> return arrayOf("I have ", "You have ", "He/she/it has ", "We have ", "You have ", "They have ")
            //I had worked
            "Past Perfect" -> return arrayOf("I had ", "You had ", "He/she/it had ", "We had ", "You had ", "They had ")
            //I will have worked
            "Future Perfect" -> return arrayOf("I will have ", "You will have ", "He/she/it will have ", "We will have ", "You will have ", "They will have ")
            //I would work
            "Present Conditional" -> return arrayOf("I would ", "You would ", "He/she/it would ", "We would ", "You would ", "They would ")
            //I would have worked
            "Perfect Conditional" -> return arrayOf("I would have ", "You would have ", "He/she/it would have ", "We would have ", "You would have ", "They would have ")
            //that I work
            "Present Subjunctive" -> return arrayOf("that I ", "that you ", "that he/she/it ", "that we ", "that you ", "that they ")
            //I may have worked
            "Perfect Subjunctive" -> return arrayOf("I may have ", "You may have ", "He/she/it may have ", "We may have ", "You may have ", "They may have ")
        }
        return arrayOf("")
    }

    fun formatLine(
        verb: String,
        tense: String,
        list: List<String>,
        language: String,
        index: Int)
            : String {
        val pronoun =
            if(language == "English") returnPronounListEnglish(tense)[index]
            else returnPronounListFrench(tense = tense, verb = verb)[index]
        return String.format("%s%s", pronoun, if(list.size <= 6) list[index] else list[index * 2])
    }

    private fun returnMin(
        nbr: Int,
        tense: String,
        language: String,
        randomizeVerbsAndTenses: RandomizeVerbsAndTenses
    ) : Int {
        val arraySize =
            if(language == "English") {
                if(tense == "Past (irr)") {
                    randomizeVerbsAndTenses.getFiftyIrregularVerbsEnglish().size
                } else {
                    randomizeVerbsAndTenses.getHundredMostCommonEnglish().size
                }
            } else {
                when(tense) {
                    "Présent (-er)" -> randomizeVerbsAndTenses.getMostCommonErFrench().size
                    "Présent (-ir)" -> randomizeVerbsAndTenses.getMostCommonIrFrench().size
                    "Présent (-re)" -> randomizeVerbsAndTenses.getMostCommonReFrench().size
                    "Présent (Réguliers)" -> randomizeVerbsAndTenses.getAllRegularsFrench().size
                    "Présent (Irréguliers)" ->
                        randomizeVerbsAndTenses.getMostCommonIrregularsFrench().size
                    "Présent (Réfléchis)", "Passé Composé (Réfléchis)" ->
                        randomizeVerbsAndTenses.getMostCommonReflexiveFrench().size
                    "Passé Composé (avoir)" -> randomizeVerbsAndTenses.getMostCommonAvoir().size
                    "Passé Composé (être)" -> randomizeVerbsAndTenses.getMostCommonEtre().size
                    else -> randomizeVerbsAndTenses.getHundredMostCommonFrench().size
                }
            }
        return Math.min(nbr, arraySize)
    }

    /*conjugation lessons separates a few special cases for better user experience,
    this method makes these options comply again with the normal tenses set in the database */
    private fun transformEnglishTense(tense: String): String {
        return if (tense == "Past (irr)") {
            "Past"
        } else tense
    }

    /*conjugation lessons separates a few special cases for better user experience,
        this method makes these options comply again with the normal tenses set in the database */
    private fun transformFrenchTense(tense: String): String {
        return when (tense) {
            "Présent (-er)", "Présent (-ir)", "Présent (-re)", "Présent (Réguliers)",
            "Présent (Irréguliers)", "Présent (Réfléchis)", "Présent (Tous)" -> "Présent"
            "Passé Composé (avoir)", "Passé Composé (être)", "Passé Composé (Réfléchis)",
            "Passé Composé (Tous)" -> "Passé Composé"
            else -> tense
        }
    }


}

