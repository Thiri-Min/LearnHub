package com.training.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MentorDataInitializer implements ApplicationRunner {

    private final MentorRepository mentorRepository;
    private final MentorSlotRepository mentorSlotRepository;

    public MentorDataInitializer(MentorRepository mentorRepository, MentorSlotRepository mentorSlotRepository) {
        this.mentorRepository = mentorRepository;
        this.mentorSlotRepository = mentorSlotRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (mentorRepository.count() > 0) {
            syncExistingMentors();
            return;
        }

        List<Mentor> mentors = List.of(
                createMentor("Ms Thiri Min", "Java, Spring Boot, OOP design patterns",
                        "10+ years teaching enterprise Java and mentoring junior developers."),
                createMentor("Mr. A", "Python, Data Structures & Algorithms",
                        "Specializes in Python automation and interview preparation coaching."),
                createMentor("Ms Thiri Min", "SQL, Database design, Performance tuning",
                        "Database expert helping students master relational modeling and queries."),
                createMentor("Ms Mai", "Git, Agile, Team collaboration",
                        "Guides learners through version control workflows and scrum practices."),
                createMentor("Mr. B", ".NET, Full-stack web development",
                        "Experienced trainer for C#, ASP.NET Core, and modern web APIs."),
                createMentor("Ms. Loan", "Software Testing, QA, Test automation",
                        "QA specialist mentoring students on test planning, manual testing, and automation basics.")
        );
        mentorRepository.saveAll(mentors);

        for (Mentor mentor : mentors) {
            generateSlotsForMentor(mentor);
        }
    }

    private void syncExistingMentors() {
        renameMentor("Dr. Aung Min", "Ms Thiri Min");
        renameMentor("Ms. Thiri Htun", "Mr. A");
        renameMentor("Mr. Kyaw Lin", "Ms Thiri Min");
        renameMentor("Ms. Su Mon", "Ms Mai");
        renameMentor("Mr. Zaw Win", "Mr. B");
        renameMentor("Ms Loan", "Mr. B");

        mentorRepository.findAll().stream()
                .filter(m -> m.getExpertise() != null && m.getExpertise().contains(".NET"))
                .filter(m -> !"Mr. B".equals(m.getName()))
                .forEach(m -> {
                    m.setName("Mr. B");
                    mentorRepository.save(m);
                });

        ensureTesterMsLoanExists();
    }

    private void ensureTesterMsLoanExists() {
        boolean testerExists = mentorRepository.findAll().stream()
                .anyMatch(m -> "Ms. Loan".equals(m.getName())
                        && m.getExpertise() != null
                        && m.getExpertise().toLowerCase().contains("test"));

        if (testerExists) {
            return;
        }

        Mentor tester = createMentor("Ms. Loan", "Software Testing, QA, Test automation",
                "QA specialist mentoring students on test planning, manual testing, and automation basics.");
        mentorRepository.save(tester);
        generateSlotsForMentor(tester);
    }

    private void generateSlotsForMentor(Mentor mentor) {
        LocalTime[] slotTimes = {
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0)
        };

        List<MentorSlot> slots = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int dayOffset = 1; dayOffset <= 10; dayOffset++) {
            LocalDate date = today.plusDays(dayOffset);
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
            for (LocalTime start : slotTimes) {
                MentorSlot slot = new MentorSlot();
                slot.setMentorId(mentor.getId());
                slot.setStartTime(LocalDateTime.of(date, start));
                slot.setEndTime(LocalDateTime.of(date, start.plusHours(1)));
                slot.setStatus("AVAILABLE");
                slots.add(slot);
            }
        }
        mentorSlotRepository.saveAll(slots);
    }

    private void renameMentor(String oldName, String newName) {
        mentorRepository.findAll().stream()
                .filter(m -> oldName.equals(m.getName()))
                .forEach(m -> {
                    m.setName(newName);
                    mentorRepository.save(m);
                });
    }

    private Mentor createMentor(String name, String expertise, String bio) {
        Mentor mentor = new Mentor();
        mentor.setName(name);
        mentor.setPosition("Technical Trainer");
        mentor.setExpertise(expertise);
        mentor.setBio(bio);
        mentor.setIconClass("fas fa-chalkboard-teacher");
        return mentor;
    }
}
