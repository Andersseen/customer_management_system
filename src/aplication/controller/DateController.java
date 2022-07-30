package aplication.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class DateController {

    private String paternFirstDay = "dd/MM/yyyy";
    private String paternLocalMonth = "dd-MMM-yyyy";
    private String paternFirstMonth = "MM/dd/yyyy";
    private String paternFirstYear = "yyyy/MM/dd";

    List<String> formatStrings = Arrays.asList(paternFirstDay,paternLocalMonth,paternFirstMonth,paternFirstYear);


    public Date tryParse(String dateString) {
        Date date = null;
        for (String formatString : formatStrings)
        {
            try
            {
                SimpleDateFormat format = new SimpleDateFormat(formatString);
//                new SimpleDateFormat(formatString).parse(dateString);
                java.util.Date parsed = format.parse(dateString);
                Date sql = new java.sql.Date(parsed.getTime());
                date =  sql;
                return date;
            }
            catch (ParseException e) {
                System.out.println();
            }
        }
        return date;
    }

//    public Date returnFormate(String value) throws ParseException {
//
//        Date date = null;
//        SimpleDateFormat format = new SimpleDateFormat(paternLocalMonth);
//
//        java.util.Date parsed = format.parse(value);
//        Date sql = new java.sql.Date(parsed.getTime());
//        date =  sql;
//        return date;
//    }
}
