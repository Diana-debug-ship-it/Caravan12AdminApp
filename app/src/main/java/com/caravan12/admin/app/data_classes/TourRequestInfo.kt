package com.caravan12.admin.app.data_classes

class TourRequestInfo{
    var applicantEmail: String? = null
    var from: String? = null
    var where: String? = null
    var dateOfDeparture: String? = null
    var howManyPeople: String? = null
    var nights: String? = null
    var comments: String? = null
    var visibility: Boolean = false

    constructor(applicantEmail: String?, from: String?, where: String?, dateOfDeparture: String?,
                howManyPeople: String?, nights: String?, comments: String?, visibility: Boolean) {
        this.applicantEmail = applicantEmail
        this.from = from
        this.where = where
        this.dateOfDeparture = dateOfDeparture
        this.howManyPeople = howManyPeople
        this.nights = nights
        this.comments = comments
        this.visibility = visibility
    }

    constructor() {}
}
