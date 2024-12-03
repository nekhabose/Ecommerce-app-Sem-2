/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.nbose1.converter;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Nekha
 */
@FacesConverter("dateConverter")
public class DateConverter implements Converter<Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd"; // Match the format in your XHTML
    private final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public Date getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return formatter.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use " + DATE_FORMAT);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Date value) {
        if (value == null) {
            return "";
        }
        return formatter.format(value);
    }
}