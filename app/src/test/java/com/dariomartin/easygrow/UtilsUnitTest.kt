package com.dariomartin.easygrow

import com.dariomartin.easygrow.utils.Utils
import com.dariomartin.easygrow.utils.Utils.DateFormat.*
import org.junit.Assert
import org.junit.Test
import java.util.*

class UtilsUnitTest {
    @Test
    fun testConvertValidDateToString_format1() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 24)
        calendar.set(Calendar.MONTH, 6)
        calendar.set(Calendar.YEAR, 2021)

        val dateString = Utils.dateToString(DD_MM_YYYY, calendar.timeInMillis)

        Assert.assertEquals(dateString, "24/07/2021")
    }

    @Test
    fun testConvertValidDateToString_format2() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 24)
        calendar.set(Calendar.MONTH, 6)
        calendar.set(Calendar.YEAR, 2021)

        val dateString = Utils.dateToString(DD_MMM_YYYY, calendar.timeInMillis)

        Assert.assertEquals(dateString, "24 jul. 2021")
    }

    @Test
    fun testConvertValidDateToString_format3() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 24)
        calendar.set(Calendar.MONTH, 6)
        calendar.set(Calendar.YEAR, 2021)
        calendar.set(Calendar.HOUR_OF_DAY, 14)
        calendar.set(Calendar.MINUTE, 35)


        val dateString = Utils.dateToString(DD_MMM_YYYY_HH_MM, calendar.timeInMillis)

        Assert.assertEquals(dateString, "24/07/2021 14:35")
    }

    @Test
    fun testConvertInvalidDateToString() {
        val dateString = Utils.dateToString(DD_MMM_YYYY_HH_MM, null)
        Assert.assertEquals(dateString, "")
    }

    @Test
    fun testConvertValidStringToDate_format1() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 24)
        calendar.set(Calendar.MONTH, 6)
        calendar.set(Calendar.YEAR, 2021)

        val result = Utils.stringToCalendar(DD_MM_YYYY, "24/07/2021")

        Assert.assertNotNull(result)
        Assert.assertEquals(
            result!!.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        Assert.assertEquals(result.get(Calendar.MONTH), calendar.get(Calendar.MONTH))
        Assert.assertEquals(result.get(Calendar.YEAR), calendar.get(Calendar.YEAR))

    }

    @Test
    fun testConvertValidStringToDate_format2() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 24)
        calendar.set(Calendar.MONTH, 6)
        calendar.set(Calendar.YEAR, 2021)

        val result = Utils.stringToCalendar(DD_MMM_YYYY, "24 jul. 2021")

        Assert.assertNotNull(result)
        Assert.assertEquals(
            result!!.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        Assert.assertEquals(result.get(Calendar.MONTH), calendar.get(Calendar.MONTH))
        Assert.assertEquals(result.get(Calendar.YEAR), calendar.get(Calendar.YEAR))
    }

    @Test
    fun testConvertValidStringToDate_format3() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 24)
        calendar.set(Calendar.MONTH, 6)
        calendar.set(Calendar.YEAR, 2021)
        calendar.set(Calendar.HOUR_OF_DAY, 14)
        calendar.set(Calendar.MINUTE, 35)

        val result = Utils.stringToCalendar(DD_MMM_YYYY_HH_MM, "24/07/2021 14:35")

        Assert.assertNotNull(result)
        Assert.assertEquals(
            result!!.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        Assert.assertEquals(result.get(Calendar.MONTH), calendar.get(Calendar.MONTH))
        Assert.assertEquals(result.get(Calendar.YEAR), calendar.get(Calendar.YEAR))
        Assert.assertEquals(result.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.HOUR_OF_DAY))
        Assert.assertEquals(result.get(Calendar.MINUTE), calendar.get(Calendar.MINUTE))
    }

    @Test
    fun testConvertInvalidStringToDate_1() {
        val result = Utils.stringToCalendar(DD_MM_YYYY, "test")
        Assert.assertNull(result)
    }

    @Test
    fun testConvertInvalidStringToDate_2() {
        val result = Utils.stringToCalendar(DD_MM_YYYY, null)
        Assert.assertNull(result)
    }

}