package com.svenruppert.expedition.planner.data.entity;

public enum DietaryRestriction {

    // Diet forms
    VEGETARIAN("diet.vegetarian.name", "diet.vegetarian.description"),
    VEGAN("diet.vegan.name", "diet.vegan.description"),
    PESCATARIAN("diet.pescatarian.name", "diet.pescatarian.description"),
    PALEO("diet.paleo.name", "diet.paleo.description"),
    KETO("diet.keto.name", "diet.keto.description"),
    LOW_CARB("diet.low_carb.name", "diet.low_carb.description"),
    INTERMITTENT_FASTING("diet.intermittent_fasting.name", "diet.intermittent_fasting.description"),

    // Intolerances
    GLUTEN_FREE("intolerance.gluten_free.name", "intolerance.gluten_free.description"),
    LACTOSE_FREE("intolerance.lactose_free.name", "intolerance.lactose_free.description"),
    FRUCTOSE_INTOLERANT("intolerance.fructose.name", "intolerance.fructose.description"),
    HISTAMINE_INTOLERANT("intolerance.histamine.name", "intolerance.histamine.description"),
    NUT_ALLERGY("allergy.nut.name", "allergy.nut.description"),
    SOY_ALLERGY("allergy.soy.name", "allergy.soy.description"),
    EGG_ALLERGY("allergy.egg.name", "allergy.egg.description"),
    SHELLFISH_ALLERGY("allergy.shellfish.name", "allergy.shellfish.description");

    private final String nameKey;
    private final String descriptionKey;

    DietaryRestriction(String nameKey, String descriptionKey) {
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    /**
     * Returns the localized name based on the current ResourceBundle.
     * @param bundle The ResourceBundle containing localized texts
     * @return The localized name
     */
    public String getName(java.util.ResourceBundle bundle) {
        return bundle.getString(nameKey);
    }

    /**
     * Returns the localized description based on the current ResourceBundle.
     * @param bundle The ResourceBundle containing localized texts
     * @return The localized description
     */
    public String getDescription(java.util.ResourceBundle bundle) {
        return bundle.getString(descriptionKey);
    }

    /**
     * Returns a localized string representation.
     * @param bundle The ResourceBundle containing localized texts
     * @return The localized string
     */
    public String toString(java.util.ResourceBundle bundle) {
        return getName(bundle) + " - " + getDescription(bundle);
    }
}
