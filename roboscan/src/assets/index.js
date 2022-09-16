import custDetailsActive from './icons/active/Roboscan_Icons_Grey_Customer_Detailes.png'
import custDetailsInactive from './icons/inactive/Roboscan_Icons_Light_Customer_Detailes.png'

import viewEddInactive from './icons/inactive/Roboscan_Icons_Light_View_EDD.png'
import viewEddActive from './icons/active/Roboscan_Icons_Grey_View_EDD.png'

import accountProfileActive from './icons/active/Roboscan_Icons_Grey_Account_Profile.png'
import accountProfileInactive from './icons/inactive/Roboscan_Icons_Light_Account_Profile.png'

import linksActive from './icons/active/Roboscan_Icons_Grey_Links.png'
import linksInactive from './icons/inactive/Roboscan_Icons_Light_Links.png'

import transactionActive from './icons/active/Roboscan_Icons_Grey_Transaction_Details.png'
import transactionInactive from './icons/inactive/Roboscan_Icons_Light_Transaction_Details.png'

import rtScreeningActive from './icons/active/Roboscan_Icons_Grey_Realtime_Screening.png'
import rtScreeningInactive from './icons/inactive/Roboscan_Icons_Light_Realtime_Screening.png'

import pastHistoryActive from './icons/active/Roboscan_Icons_Grey_Pav_History.png'
import pastHistoryInactive from './icons/inactive/Roboscan_Icons_Light_Pav_History.png'

import relatedPartiesActive from './icons/active/Roboscan_Icons_Grey_Related_Parties.png'
import relatedPartiesInactive from './icons/inactive/Roboscan_Icons_Light_Related_Parties.png'

import ringsideViewActive from './icons/active/Roboscan_Icons_Grey_Ringside_View.png'
import ringsideViewInactive from './icons/inactive/Roboscan_Icons_Light_Ringside_view.png'

import userCommentsActive from './icons/active/Roboscan_Icons_Grey_User_Comments.png'
import userCommentsInactive from './icons/inactive/Roboscan_Icons_Light_User_Comments.png'

import actionItemActive from './icons/active/Roboscan_Icons_Grey_Action_Item.png'
import actionItemInactive from './icons/inactive/Roboscan_Icons_Light_Action_Item.png'

import summaryDetailsActive from './icons/active/Roboscan_Icons_Grey_Summury.png'
import summaryDetailsInactive from './icons/inactive/Roboscan_Icons_Light_Summury.png'

import viewCommentsActive from './icons/active/Roboscan_Icons_Grey_View_Comment.png'
import viewCommentsInactive from './icons/inactive/Roboscan_Icons_Light_View_Comments.png'

import attachActive from './icons/active/Roboscan_Icons_Grey_Attach_Evidence.png'
import attachInactive from './icons/inactive/Roboscan_Icons_Light_Attach_Evidence.png'

import emailActive from './icons/active/Roboscan_Icons_Grey_Email.png'
import emailInactive from './icons/inactive/Roboscan_Icons_Light_Email.png'

import entityLinkActive from './icons/active/Roboscan_Icons_Grey_Entity_Link.png'
import entityLinkInactive from './icons/inactive/Roboscan_Icons_Light_Entity_Link.png'

const icons = {
    custDetailsActive,
    custDetailsInactive,
    viewEddActive,
    viewEddInactive,
    accountProfileActive,
    accountProfileInactive,
    linksActive,
    linksInactive,
    transactionActive,
    transactionInactive,
    rtScreeningActive,
    rtScreeningInactive,
    pastHistoryActive,
    pastHistoryInactive,
    relatedPartiesActive,
    relatedPartiesInactive,
    ringsideViewActive,
    ringsideViewInactive,
    userCommentsActive,
    userCommentsInactive,
    actionItemActive,
    actionItemInactive,
    summaryDetailsActive,
    summaryDetailsInactive,
    viewCommentsActive,
    viewCommentsInactive,
    attachActive,
    attachInactive,
    emailActive,
    emailInactive,
    entityLinkActive,
    entityLinkInactive,
}

const getIconByKey = (key) => {
    return icons[key]
}

export default getIconByKey
