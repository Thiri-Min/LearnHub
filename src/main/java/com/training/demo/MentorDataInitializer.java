package com.training.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MentorDataInitializer implements ApplicationRunner {

    private final MentorRepository mentorRepository;
    private final MentorSlotRepository mentorSlotRepository;
    private final MentorBookingRepository mentorBookingRepository;
    private final MentorChatMessageRepository mentorChatMessageRepository;
    private final TransactionTemplate transactionTemplate;

    public MentorDataInitializer(MentorRepository mentorRepository,
                                 MentorSlotRepository mentorSlotRepository,
                                 MentorBookingRepository mentorBookingRepository,
                                 MentorChatMessageRepository mentorChatMessageRepository,
                                 PlatformTransactionManager transactionManager) {
        this.mentorRepository = mentorRepository;
        this.mentorSlotRepository = mentorSlotRepository;
        this.mentorBookingRepository = mentorBookingRepository;
        this.mentorChatMessageRepository = mentorChatMessageRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    public void run(ApplicationArguments args) {
        if (mentorRepository.count() > 0) {
            transactionTemplate.executeWithoutResult(status -> syncExistingMentors());
            return;
        }

        List<Mentor> mentors = List.of(
                createMentor("Ms. Thiri Min", "Java, Spring Boot, OOP design patterns",
                        "10+ years teaching enterprise Java and mentoring junior developers.",
                        "Technical Trainer", "fab fa-java"),
                createMentor("Mr. Tu", "Microsoft Azure, Cloud services, Solution architecture",
                        "Cloud trainer specializing in Azure infrastructure, deployment pipelines, and scalable solution design.",
                        "Cloud Technical Trainer", "fas fa-cloud"),
                createMentor("Mr. Hai", "Python, Data Structures & Algorithms",
                        "Specializes in Python development, automation, and interview preparation coaching.",
                        "Technical Trainer", "fab fa-python"),
                createMentor("Ms. Mai", "Front-End Technology — HTML, CSS, JavaScript, React, Angular",
                        "Front-end specialist guiding learners through responsive UI, modern frameworks, and clean component design.",
                        "Front-End Technical Trainer", "fas fa-laptop-code"),
                createMentor("Mr. Hieu", ".NET, Full-stack web development",
                        "Experienced trainer for C#, ASP.NET Core, and modern web APIs.",
                        "Technical Trainer", "fab fa-microsoft"),
                createMentor("Ms. Loan", "Software Testing, QA, Test automation",
                        "QA specialist mentoring students on test planning, manual testing, and automation basics.",
                        "QA Technical Trainer", "fas fa-clipboard-check")
        );
        mentorRepository.saveAll(mentors);

        for (Mentor mentor : mentors) {
            generateSlotsForMentor(mentor);
        }
    }

    private void syncExistingMentors() {
        renameMentor("Dr. Aung Min", "Ms Thiri Min");
        renameMentor("Ms. Thiri Htun", "Mr. Hai");
        renameMentor("Mr. A", "Mr. Tu");
        renameMentor("Ms. Su Mon", "Ms Mai");
        renameMentor("Mr. Zaw Win", "Mr. Hieu");
        renameMentor("Mr. B", "Mr. Hieu");
        renameMentor("Ms Loan", "Ms. Loan");

        ensureTesterMsLoanExists();
        updateMsMaiFrontEnd();
        updateMrTuAzure();
        updateMrHaiPython();
        updateMrHieuDotNet();
        updateMsLoanQa();
        removeDuplicateThiriMin();
        updateMsThiriMinProfile();
    }

    private void removeDuplicateThiriMin() {
        List<Mentor> thiriMentors = mentorRepository.findAll().stream()
                .filter(m -> m.getName() != null && m.getName().replace(".", "").trim().toLowerCase().contains("thiri"))
                .sorted(Comparator.comparing(Mentor::getId))
                .toList();

        if (thiriMentors.isEmpty()) {
            return;
        }

        Mentor keep = thiriMentors.stream()
                .filter(m -> m.getExpertise() != null
                        && (m.getExpertise().contains("Java") || m.getExpertise().contains("Spring")))
                .findFirst()
                .orElse(thiriMentors.get(0));

        for (Mentor mentor : thiriMentors) {
            if (!mentor.getId().equals(keep.getId())) {
                deleteMentorData(mentor.getId());
                mentorRepository.delete(mentor);
            }
        }
    }

    private void deleteMentorData(Long mentorId) {
        mentorBookingRepository.deleteByMentorId(mentorId);
        mentorChatMessageRepository.deleteByMentorId(mentorId);
        mentorSlotRepository.deleteByMentorId(mentorId);
    }

    private void updateMsThiriMinProfile() {
        mentorRepository.findAll().stream()
                .filter(m -> m.getName() != null && m.getName().replace(".", "").trim().toLowerCase().contains("thiri"))
                .forEach(m -> {
                    m.setName("Ms. Thiri Min");
                    m.setPosition("Technical Trainer");
                    m.setExpertise("Java, Spring Boot, OOP design patterns");
                    m.setBio("10+ years teaching enterprise Java and mentoring junior developers.");
                    m.setIconClass("fab fa-java");
                    mentorRepository.save(m);
                });
    }

    private void updateMrTuAzure() {
        mentorRepository.findAll().stream()
                .filter(m -> m.getName() != null
                        && (m.getName().replace(".", "").trim().equalsIgnoreCase("Mr Tu")
                        || "Mr. A".equals(m.getName())))
                .forEach(m -> {
                    m.setName("Mr. Tu");
                    m.setPosition("Cloud Technical Trainer");
                    m.setExpertise("Microsoft Azure, Cloud services, Solution architecture");
                    m.setBio("Cloud trainer specializing in Azure infrastructure, deployment pipelines, and scalable solution design.");
                    m.setIconClass("fas fa-cloud");
                    mentorRepository.save(m);
                });
    }

    private void updateMrHaiPython() {
        boolean haiPythonExists = mentorRepository.findAll().stream()
                .anyMatch(m -> m.getName() != null
                        && m.getName().replace(".", "").trim().equalsIgnoreCase("Mr Hai")
                        && m.getExpertise() != null
                        && m.getExpertise().toLowerCase().contains("python"));

        if (!haiPythonExists) {
            boolean updated = false;
            for (Mentor m : mentorRepository.findAll()) {
                if (m.getExpertise() != null
                        && m.getExpertise().toLowerCase().contains("python")
                        && m.getName() != null
                        && !m.getName().replace(".", "").trim().equalsIgnoreCase("Mr Tu")) {
                    m.setName("Mr. Hai");
                    m.setPosition("Technical Trainer");
                    m.setExpertise("Python, Data Structures & Algorithms");
                    m.setBio("Specializes in Python development, automation, and interview preparation coaching.");
                    m.setIconClass("fab fa-python");
                    mentorRepository.save(m);
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                Mentor hai = createMentor("Mr. Hai", "Python, Data Structures & Algorithms",
                        "Specializes in Python development, automation, and interview preparation coaching.",
                        "Technical Trainer", "fab fa-python");
                mentorRepository.save(hai);
                generateSlotsForMentor(hai);
            }
        } else {
            mentorRepository.findAll().stream()
                    .filter(m -> m.getName() != null && m.getName().replace(".", "").trim().equalsIgnoreCase("Mr Hai"))
                    .forEach(m -> {
                        m.setName("Mr. Hai");
                        m.setPosition("Technical Trainer");
                        m.setExpertise("Python, Data Structures & Algorithms");
                        m.setBio("Specializes in Python development, automation, and interview preparation coaching.");
                        m.setIconClass("fab fa-python");
                        mentorRepository.save(m);
                    });
        }
    }

    private void updateMrHieuDotNet() {
        mentorRepository.findAll().stream()
                .filter(m -> m.getExpertise() != null && m.getExpertise().contains(".NET"))
                .forEach(m -> {
                    m.setName("Mr. Hieu");
                    m.setPosition("Technical Trainer");
                    m.setExpertise(".NET, Full-stack web development");
                    m.setBio("Experienced trainer for C#, ASP.NET Core, and modern web APIs.");
                    m.setIconClass("fab fa-microsoft");
                    mentorRepository.save(m);
                });
    }

    private void updateMsMaiFrontEnd() {
        mentorRepository.findAll().stream()
                .filter(m -> m.getName() != null && m.getName().replace(".", "").trim().equalsIgnoreCase("Ms Mai"))
                .forEach(m -> {
                    m.setName("Ms. Mai");
                    m.setPosition("Front-End Technical Trainer");
                    m.setExpertise("Front-End Technology — HTML, CSS, JavaScript, React, Angular");
                    m.setBio("Front-end specialist guiding learners through responsive UI, modern frameworks, and clean component design.");
                    m.setIconClass("fas fa-laptop-code");
                    mentorRepository.save(m);
                });
    }

    private void updateMsLoanQa() {
        mentorRepository.findAll().stream()
                .filter(m -> m.getName() != null && m.getName().replace(".", "").trim().equalsIgnoreCase("Ms Loan"))
                .forEach(m -> {
                    m.setName("Ms. Loan");
                    m.setPosition("QA Technical Trainer");
                    m.setExpertise("Software Testing, QA, Test automation");
                    m.setBio("QA specialist mentoring students on test planning, manual testing, and automation basics.");
                    m.setIconClass("fas fa-clipboard-check");
                    mentorRepository.save(m);
                });
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
                "QA specialist mentoring students on test planning, manual testing, and automation basics.",
                "QA Technical Trainer", "fas fa-clipboard-check");
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
        return createMentor(name, expertise, bio, "Technical Trainer", "fas fa-chalkboard-teacher");
    }

    private Mentor createMentor(String name, String expertise, String bio, String position, String iconClass) {
        Mentor mentor = new Mentor();
        mentor.setName(name);
        mentor.setPosition(position);
        mentor.setExpertise(expertise);
        mentor.setBio(bio);
        mentor.setIconClass(iconClass);
        return mentor;
    }
}
