package com.caravan12.admin.app.data_classes

class TourRequestInfo{
    var email: String? = null
    var from: String? = null
    var destination: String? = null
    var dateOfDeparture: String? = null
    var adults: String? = null
    var nights: String? = null
    var comments: String? = null
    var children: String? = null
    var meals: String? = null
    var rating: String? = null
    var visibility: Boolean = false
    var id: String? = null

    constructor(applicantEmail: String?, from: String?, destination: String?, dateOfDeparture: String?,
                adults: String?, nights: String?, comments: String?, children: String?,
                meals: String?, rating: String?, visibility: Boolean, id: String?) {
        this.email = applicantEmail
        this.from = from
        this.destination = destination
        this.dateOfDeparture = dateOfDeparture
        this.adults = adults
        this.nights = nights
        this.comments = comments
        this.children = children
        this.meals = meals
        this.rating = rating
        this.visibility = visibility
        this.id = id
    }

    constructor() {}
}
