package com.karthee.interviewsample.data.models

import com.karthee.interviewsample.domain.models.SectionItemObj
import com.karthee.interviewsample.domain.models.SectionObj

fun SectionItem.toSectionItemObj() = SectionItemObj(title,image)
fun Section.toSectionObj() = SectionObj(sectionType,items.map { it.toSectionItemObj() })