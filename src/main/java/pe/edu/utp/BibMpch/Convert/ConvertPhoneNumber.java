package pe.edu.utp.BibMpch.Convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConvertPhoneNumber implements AttributeConverter<String,String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute != null ? attribute.trim() : null;
    }
    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData != null ? dbData.trim() : null;
    }
}
