package com.company.verbzz_app.utils

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class RandomizeVerbsAndTenses : ViewModel() {
    private val hundredMostCommonEnglish = arrayOf(
        "be", "have", "do", "say", "go", "get", "make", "know", "think"
        , "take", "see", "come", "want", "look", "use", "find", "give", "tell", "work"
        , "call", "try", "ask", "need", "feel", "become", "leave", "put", "mean", "keep"
        , "let", "begin", "seem", "help", "talk", "turn", "start", "show", "hear", "play"
        , "run", "move", "like", "live", "believe", "hold", "bring", "happen", "write"
        , "provide", "sit", "stand", "lose", "pay", "meet", "include", "continue", "set"
        , "learn", "change", "lead", "understand", "watch", "follow", "stop", "create"
        , "speak", "read", "allow", "add", "spend", "grow", "open", "walk", "win", "offer"
        , "remember", "love", "consider", "appear", "buy", "wait", "serve", "die", "send"
        , "expect", "build", "stay", "fall", "cut", "reach", "kill", "remain", "suggest"
        , "raise", "pass", "sell", "require", "report", "decide", "pull"
    )

    private val fiftyIrregularVerbsEnglish = arrayOf("say", "make", "go", "take", "come"
        , "see", "know", "get", "give", "find", "think"
        , "tell", "become", "show", "leave", "feel", "put", "bring", "begin", "keep"
        , "hold", "write", "stand", "hear", "let", "mean", "set", "meet", "run", "pay"
        , "sit", "speak", "lie", "lead", "read", "grow", "lose", "fall", "send", "build"
        , "understand", "draw", "break", "spend", "cut", "rise", "drive", "buy", "wear", "choose"
    )

    private val hundredMostCommonFrench = arrayOf("être", "avoir", "pouvoir", "faire"
        , "mettre", "dire", "devoir", "prendre", "donner", "aller", "vouloir", "savoir"
        , "falloir", "voir", "demander", "trouver", "rendre", "venir", "passer"
        , "comprendre", "rester", "tenir", "porter", "parler", "montrer", "continuer"
        , "penser", "suivre", "connaître", "croire", "commencer", "compter", "entendre"
        , "attendre", "remettre", "appeler", "permettre", "occuper", "devenir", "partir"
        , "décider", "arriver", "servir", "sembler", "revenir", "laisser", "recevoir"
        , "répondre", "vivre", "rappeler", "présenter", "accepter", "agir", "poser"
        , "jouer", "reconnaître", "choisir", "toucher", "aimer", "retrouver", "perdre"
        , "expliquer", "considérer", "ouvrir", "gagner", "exister", "refuser", "lire"
        , "réussir", "changer", "travailler", "représenter", "assurer", "essayer"
        , "empêcher", "sortir", "reprendre", "mener", "appartenir", "risquer"
        , "concerner", "apprendre", "rencontrer", "créer", "obtenir", "chercher", "entrer"
        , "proposer", "apporter", "utiliser", "atteindre", "tenter", "importer", "ajouter"
        , "produire", "préparer", "relever", "écrire", "défendre", "tirer"
    )

    private val mostCommonErFrench = arrayOf("aimer", "arriver", "chanter"
        , "chercher", "commencer", "danser", "demander", "donner", "écouter", "étudier"
        , "fermer", "jouer", "laver", "nager", "parler", "passer", "porter", "rêver"
        , "sembler", "travailler"
    )

    private val mostCommonIrFrench = arrayOf("abolir", "agir", "bâtir"
        , "choisir", "établir", "finir", "grossir", "maigrir", "nourrir"
        , "obéir", "réfléchir", "remplir", "réussir", "rougir", "vieillir"
    )

    private val mostCommonReFrench = arrayOf("attendre", "défendre", "descendre"
        , "entendre", "étendre", "fondre", "pendre", "perdre", "prétendre", "rendre"
        , "répandre", "répondre", "vendre"
    )

    private val allRegularsFrench = arrayOf("aimer", "arriver", "chanter"
        , "chercher", "commencer", "danser", "demander", "donner", "écouter", "étudier"
        , "fermer", "jouer", "laver", "nager", "parler", "passer", "porter", "rêver"
        , "sembler", "travailler", "abolir", "agir", "bâtir"
        , "choisir", "établir", "finir", "grossir", "maigrir", "nourrir"
        , "obéir", "réfléchir", "remplir", "réussir", "rougir", "vieillir", "attendre"
        , "défendre", "descendre", "entendre", "étendre", "fondre", "pendre", "perdre"
        , "prétendre", "rendre", "répandre", "répondre", "vendre"
    )

    private val mostCommonIrregularsFrench = arrayOf("aller", "avoir", "dire"
        , "être", "faire", "falloir", "pouvoir", "savoir", "voir", "vouloir"
        , "devoir", "prendre", "venir", "tenir", "comprendre", "suivre", "appeler"
        , "croire", "connaître", "permettre"
    )

    private val mostCommonReflexiveFrench = arrayOf("amuser", "appeler"
        , "asseoir", "baigner", "coucher", "dépêcher", "habiller"
        , "laver", "lever", "passer", "promener", "rappeler", "réveiller", "trouver"
    )

    private val mostCommonAvoir = arrayOf("accompagner", "agir", "aider"
        , "aimer", "apporter", "attendre", "avoir", "bâtir", "boire", "choisir", "comprendre"
        , "conduire", "connaître", "courir", "couvrir", "croire",  "défendre", "désobéir"
        , "devoir", "dire", "écrire", "être", "faire", "finir", "guérir", "lire"
        , "offrir", "ouvrir", "penser", "permettre", "prendre", "préparer", "savoir", "vouloir"
    )

    private val mostCommonEtre = arrayOf("aller", "venir", "rentrer"
        , "sortir", "arriver", "partir", "monter", "descendre", "naître", "mourir"
        , "décéder", "rester", "passer", "tomber", "retourner", "apparaître"
        , "devenir", "revenir"
    )


    fun getHundredMostCommonEnglish(): Array<String> {
        return hundredMostCommonEnglish
    }

    fun getFiftyIrregularVerbsEnglish(): Array<String> {
        return fiftyIrregularVerbsEnglish
    }

    fun getHundredMostCommonFrench(): Array<String> {
        return hundredMostCommonFrench
    }

    fun getMostCommonErFrench(): Array<String> {
        return mostCommonErFrench
    }

    fun getMostCommonIrFrench(): Array<String> {
        return mostCommonIrFrench
    }

    fun getMostCommonReFrench(): Array<String> {
        return mostCommonReFrench
    }

    fun getAllRegularsFrench(): Array<String> {
        return allRegularsFrench
    }

    fun getMostCommonIrregularsFrench(): Array<String> {
        return mostCommonIrregularsFrench
    }

    fun getMostCommonReflexiveFrench(): Array<String> {
        return mostCommonReflexiveFrench
    }

    fun getMostCommonAvoir(): Array<String> {
        return mostCommonAvoir
    }

    fun getMostCommonEtre(): Array<String> {
        return mostCommonEtre
    }

    fun returnRandomIndex(max: Int): Int {
        return (Math.random() * max).toInt()
    }
}