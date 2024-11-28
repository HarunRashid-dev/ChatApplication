package com.eazyalgo.chatapplication

class Message {
    var massage: String? = null
    var senderId: String? = null

    constructor(){}
    constructor(message: String?, senderId: String?){
        this.message = message
        this.senderId = senderId
    }

}