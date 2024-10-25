/*
Repräsentiert eine Aufgabe, die der Spieler lösen muss.
description erklärt, was der Spieler tun soll.
solution ist die korrekte Lösung der Aufgabe.
hints bietet zusätzliche Informationen, die dem Spieler bei der Lösung helfen können.
 */

case class Aufgabe(
                 description: String,
                 solution: Boolean,
                 hints: String
               )