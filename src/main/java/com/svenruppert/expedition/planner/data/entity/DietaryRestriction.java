package com.svenruppert.expedition.planner.data.entity;

/**
 * An enum representing various dietary restrictions and food intolerances.
 * Uses i18n keys for internationalized texts.
 */
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

    /**
     * Returns the name key for internationalization.
     * @return The name key
     */
    public String getNameKey() {
        return nameKey;
    }

    /**
     * Returns the description key for internationalization.
     * @return The description key
     */
    public String getDescriptionKey() {
        return descriptionKey;
    }
}
