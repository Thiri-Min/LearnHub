package com.training.demo;



import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.List;

import java.util.Map;

import java.util.Optional;



/**

 * English for IT learning units with in-app lecture readings.

 */

public final class EnglishItCatalog {



    public record Unit(

            String id,

            String title,

            String cefr,

            String category,

            String description,

            int vocabCount,

            int activityCount,

            boolean hasReading,

            List<String> sampleVocab) {

    }



    private static final List<Unit> UNITS = List.of(

            unit("welcome", "Welcome to English for IT", "A2", "Foundations",

                    "Core IT terms, classroom language, and how to talk about your tech learning goals."),

            unit("operating-systems", "Operating Systems", "B1", "Foundations",

                    "Describe OS features, file management, and everyday troubleshooting vocabulary."),

            unit("introduction-to-software", "Introduction to Software", "B2", "Development",

                    "Explain applications, licenses, versions, and how teams ship software products."),

            unit("it-careers", "IT Careers", "B2", "Careers",

                    "Discuss roles, responsibilities, and career paths across the technology industry."),

            unit("software-applications", "Software Applications", "B1", "Foundations",

                    "Talk about productivity tools, browsers, and common workplace applications."),

            unit("the-internet", "The Internet", "B2", "Networking",

                    "Master vocabulary for URLs, protocols, connectivity, and online services."),

            unit("computer-ethics", "Computer Ethics", "B2", "Professional",

                    "Discuss privacy, intellectual property, and responsible technology use."),

            unit("tech-support", "English for Tech Support", "B1", "Professional",

                    "Handle tickets, explain issues clearly, and guide users through fixes."),

            unit("cybersecurity", "Cybersecurity Principles", "C1", "Security",

                    "Advanced terms for threats, defenses, and secure system design."),

            unit("programmers", "English for Programmers", "C1", "Development",

                    "Read docs, name variables well, and communicate clearly in code reviews."),

            unit("programming-languages", "Top Programming Languages", "B2", "Development",

                    "Compare languages, paradigms, and when to choose each stack."),

            unit("networking", "Networking", "B2", "Networking",

                    "Explain routers, subnets, DNS, and how data travels across networks."),

            unit("artificial-intelligence", "Artificial Intelligence", "C1", "Emerging Tech",

                    "Discuss ML models, training data, ethics, and AI in the workplace."),

            unit("qa", "QA (Quality Assurance)", "C1", "Professional",

                    "Describe test plans, defects, regression, and release quality gates."),

            unit("it-slang", "IT Slang: Part I", "B2", "Communication",

                    "Understand informal tech talk used in stand-ups, chats, and team culture.")

    );



    private EnglishItCatalog() {

    }



    public static List<Unit> getUnits() {

        return UNITS;

    }



    public static Optional<Unit> findById(String id) {

        if (id == null || id.isBlank()) {

            return Optional.empty();

        }

        return UNITS.stream().filter(u -> u.id().equals(id.trim())).findFirst();

    }



    public static List<String> getCefrLevels() {

        return List.of("A2", "B1", "B2", "C1");

    }



    public static List<String> getCategories() {

        List<String> categories = new ArrayList<>();

        for (Unit unit : UNITS) {

            if (!categories.contains(unit.category())) {

                categories.add(unit.category());

            }

        }

        return categories;

    }



    public static Map<String, Object> getSummary() {

        Map<String, Object> summary = new LinkedHashMap<>();

        summary.put("unitCount", UNITS.size());

        summary.put("vocabTotal", UNITS.stream().mapToInt(Unit::vocabCount).sum());

        summary.put("activityTotal", UNITS.stream().mapToInt(Unit::activityCount).sum());

        summary.put("readingCount", UNITS.stream().filter(Unit::hasReading).count());

        return summary;

    }



    private static Unit unit(String id, String title, String cefr, String category, String description) {

        return new Unit(id, title, cefr, category, description,

                EnglishItVocabulary.count(id),

                7,

                true,

                EnglishItVocabulary.sampleTerms(id, 5));

    }

}


