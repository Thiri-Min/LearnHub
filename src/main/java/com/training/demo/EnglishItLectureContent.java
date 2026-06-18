package com.training.demo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-app lecture readings for English for IT units.
 */
public final class EnglishItLectureContent {

    public record VocabEntry(String term, String definition) {
    }

    public record Lecture(
            String unitId,
            List<String> objectives,
            List<String> readingParagraphs,
            List<VocabEntry> vocabulary,
            String grammarTip,
            List<String> practicePhrases,
            List<String> reflectionPrompts) {
    }

    private static final Map<String, Lecture> LECTURES = new LinkedHashMap<>();

    static {
        registerAll();
    }

    private EnglishItLectureContent() {
    }

    public static Optional<Lecture> getLecture(String unitId) {
        return Optional.ofNullable(LECTURES.get(unitId));
    }

    private static void registerAll() {
        lecture("welcome",
                List.of(
                        "Name basic parts of a computer and common software actions.",
                        "Use simple present tense to describe daily IT tasks.",
                        "Introduce yourself in a classroom or internship setting."),
                List.of(
                        "Welcome to your first step in professional English for technology. In IT teams, you will hear words like hardware, software, and user interface every day. Hardware means the physical parts of a computer — the screen, keyboard, and processor. Software is the programs that run on the machine, such as a browser or a code editor.",
                        "When you start a new course or job, you often need to log in to systems. You enter your username and password, then download files or open applications. These are simple but important actions. Clear communication helps your trainer or mentor support you faster.",
                        "In class, you can say: \"I am learning Java and front-end development.\" or \"I need help with my login.\" Short, polite sentences are enough at A2 level. Focus on key nouns and verbs: open, save, click, install, restart."),
                EnglishItVocabulary.getVocabulary("welcome"),
                "At A2 level, use the present simple for routines: \"I open the IDE every morning.\" \"She saves her work on Friday.\"",
                List.of(
                        "\"I am a student in the FSA internship program.\"",
                        "\"My laptop is slow. I need to restart it.\"",
                        "\"Can you help me with this login page?\""),
                List.of(
                        "Which IT words do you already know in English?",
                        "Write three things you do on a computer every day."));

        lecture("operating-systems",
                List.of(
                        "Describe common operating system tasks and settings.",
                        "Explain simple technical problems to a classmate.",
                        "Use phrasal verbs common in IT support conversations."),
                List.of(
                        "An operating system (OS) manages your computer. Windows, macOS, and Linux are common examples. When you boot up your machine, the OS loads first and prepares applications to run. If the system is slow, you may open the task manager to see which programs use the most memory.",
                        "Files live in a file system organized as folders and paths. Permissions control who can read or change a file. In internships, you may need to request access to a shared drive or repository. Always use polite language: \"Could I get read access to this folder?\"",
                        "Updates are important for security and stability. Before you install a major update, save your work and close critical apps. If something fails, describe the problem clearly: what you clicked, the error message, and whether you can reproduce the issue."),
                EnglishItVocabulary.getVocabulary("operating-systems"),
                "Use \"need to + verb\" for requirements: \"You need to update the driver.\" Use \"should\" for advice: \"You should restart after the patch.\"",
                List.of(
                        "\"After I boot up, the Wi-Fi icon shows no connection.\"",
                        "\"I don't have permission to edit this file.\"",
                        "\"The issue happens every time I open the app.\""),
                List.of(
                        "What OS do you use most often?",
                        "How would you ask a mentor for folder access?"));

        lecture("software-applications",
                List.of(
                        "Name common workplace applications and their purposes.",
                        "Give clear instructions using imperative verbs.",
                        "Discuss syncing and cloud storage in simple terms."),
                List.of(
                        "Modern teams use many applications: email, chat, spreadsheets, and browsers. A spreadsheet helps you track tasks, budgets, or test results. Learn useful shortcuts — they save time and impress colleagues.",
                        "The toolbar and menu bar give quick access to features. If you are unsure, use the search box inside the app. Many tools also offer sync, so your files are available on another device through cloud storage.",
                        "When helping a teammate, give steps in order: \"Open the file, click Share, and add the team email.\" Short imperative sentences are clear and professional at B1 level."),
                EnglishItVocabulary.getVocabulary("software-applications"),
                "Imperatives for instructions: \"Click Save.\" \"Select the correct sheet.\" Add \"please\" to sound polite: \"Please refresh the page.\"",
                List.of(
                        "\"Use Ctrl+S to save your changes.\"",
                        "\"The document syncs automatically overnight.\"",
                        "\"I uploaded the report to cloud storage.\""),
                List.of(
                        "Which application do you use most for teamwork?",
                        "Write three imperative steps to share a document."));

        lecture("tech-support",
                List.of(
                        "Open and update support tickets with clear language.",
                        "Ask diagnostic questions professionally.",
                        "Escalate issues when necessary."),
                List.of(
                        "Tech support agents receive tickets describing user problems. A good ticket includes the user's environment, the expected result, and the actual result. Start with empathy: \"I understand this is blocking your work. Let's troubleshoot together.\"",
                        "Troubleshooting follows logical steps: confirm the symptom, check recent changes, test a workaround, and document what you tried. Ask focused questions: \"When did the error first appear?\" \"Can you reproduce it on another browser?\"",
                        "If you cannot fix the issue within SLA time, escalate to a senior engineer. Summarize the case clearly so the next person does not repeat the same tests. Professional tone matters even in chat messages."),
                EnglishItVocabulary.getVocabulary("tech-support"),
                "Use past simple for events: \"The error appeared after the update.\" Use present perfect for recent relevance: \"I have restarted the service twice.\"",
                List.of(
                        "\"I've documented the steps we tried so far.\"",
                        "\"Could you send a screenshot of the error?\"",
                        "\"I'll escalate this to the platform team.\""),
                List.of(
                        "What information should every support ticket include?",
                        "Role-play: a user cannot log in. What do you ask first?"));

        lecture("introduction-to-software",
                List.of(
                        "Explain software types, releases, and licensing models.",
                        "Compare open source and proprietary software.",
                        "Describe deployment in team communication."),
                List.of(
                        "Software ranges from mobile apps to enterprise platforms. Teams plan releases that bundle new features, bug fixes, and security patches. A patch is a small update; a major release may change the user experience significantly.",
                        "Open source software allows anyone to view and modify the code, often under specific licenses. Proprietary software is owned by a company and distributed under commercial terms. Both models are common in industry — choose based on cost, support, and compliance needs.",
                        "Deployment is the process of making software available to users. In agile teams, deployment may happen weekly or daily. Clear release notes help support staff and customers understand what changed."),
                EnglishItVocabulary.getVocabulary("introduction-to-software"),
                "Use passive voice in formal updates: \"The application was deployed on Sunday.\" \"A critical patch was released yesterday.\"",
                List.of(
                        "\"We are preparing the next release for QA.\"",
                        "\"This library is open source under the MIT license.\"",
                        "\"Deployment to production is scheduled for 10 p.m.\""),
                List.of(
                        "Name one open source tool you use and why.",
                        "What should a good release note include?"));

        lecture("it-careers",
                List.of(
                        "Describe common IT roles and responsibilities.",
                        "Talk about deadlines and teamwork professionally.",
                        "Present your skills in an internship or interview."),
                List.of(
                        "IT careers include developers, testers, business analysts, DevOps engineers, and project coordinators. A developer writes and maintains code; a stakeholder may be a client or product owner who defines business goals. Successful teams align technical work with stakeholder expectations.",
                        "During onboarding, you learn tools, processes, and team culture. You may be assigned a mentor who reviews your tasks and gives feedback. Always clarify deadlines: \"Do you need this by end of day Friday or Monday morning?\"",
                        "A portfolio or GitHub profile shows your practical skills. In interviews, use the STAR method: Situation, Task, Action, Result. Example: \"In my group project, I built the login API and reduced test failures by 30%.\""),
                EnglishItVocabulary.getVocabulary("it-careers"),
                "Use modal verbs for ability and obligation: \"I can maintain REST APIs.\" \"We must meet the sprint deadline.\"",
                List.of(
                        "\"I'm responsible for front-end components in this sprint.\"",
                        "\"My mentor suggested I improve my unit tests.\"",
                        "\"I delivered the feature before the deadline.\""),
                List.of(
                        "Which IT role interests you most and why?",
                        "Draft two sentences about a project for your portfolio."));

        lecture("the-internet",
                List.of(
                        "Explain how users access websites and online services.",
                        "Use networking terms in meetings and documentation.",
                        "Describe connectivity problems clearly."),
                List.of(
                        "The internet connects billions of devices through standardized protocols. When you type a domain name, DNS translates it to an IP address so your browser can load the site. HTTPS encrypts data between your browser and the server, protecting passwords and personal information.",
                        "Bandwidth affects how fast data travels; latency is the delay before data starts arriving. Firewalls filter traffic to block unauthorized access. In office networks, IT policies may restrict certain ports or applications.",
                        "When reporting connectivity issues, specify location, device, and error messages. \"The VPN connects, but internal tools time out\" is more useful than \"the internet is broken.\""),
                EnglishItVocabulary.getVocabulary("the-internet"),
                "Use conditional sentences for diagnosis: \"If the DNS fails, the site won't load.\" \"Unless the certificate is valid, the browser will warn users.\"",
                List.of(
                        "\"Latency spikes during peak hours affect our demos.\"",
                        "\"Please check whether port 443 is open on the firewall.\"",
                        "\"The API endpoint resolves correctly in DNS.\""),
                List.of(
                        "What is the difference between bandwidth and latency?",
                        "How would you report a slow VPN to IT support?"));

        lecture("computer-ethics",
                List.of(
                        "Discuss privacy, consent, and responsible data use.",
                        "Explain copyright and plagiarism in technical work.",
                        "Use ethical vocabulary in team discussions."),
                List.of(
                        "Technology professionals handle sensitive data daily. Data privacy means protecting personal information according to law and company policy. Users must give informed consent before you collect or share their data. Compliance teams help ensure products meet regulations such as GDPR.",
                        "Copyright protects creative and technical works. Copying code or documentation without permission can create legal risk. Plagiarism — presenting others' work as your own — is unacceptable in academic and professional settings. Always cite sources and respect licenses.",
                        "Ethical design asks: \"Who benefits? Who might be harmed?\" Teams should document decisions, especially when using analytics, AI, or automated monitoring. Speaking up about ethical concerns is part of professional maturity."),
                EnglishItVocabulary.getVocabulary("computer-ethics"),
                "Use modal verbs for obligation: \"We must anonymize test data.\" \"Developers should not store passwords in plain text.\"",
                List.of(
                        "\"We need user consent before enabling analytics.\"",
                        "\"This library requires attribution in our documentation.\"",
                        "\"The feature was removed to reduce privacy risk.\""),
                List.of(
                        "Why is test data anonymization important?",
                        "Give one example of an ethical dilemma in software."));

        lecture("programming-languages",
                List.of(
                        "Compare programming languages and paradigms.",
                        "Explain when to choose a language or framework.",
                        "Discuss runtime and syntax differences clearly."),
                List.of(
                        "Programming languages differ in syntax, performance, and ecosystem. Compiled languages like Java or C++ translate to machine code before running. Interpreted languages like Python or JavaScript are executed by a runtime, often with faster development cycles.",
                        "A framework provides structure and reusable components — for example, Spring for Java backends or React for front-end UIs. Teams choose stacks based on talent availability, performance needs, and integration requirements.",
                        "When comparing languages in a meeting, stay objective: \"Go is strong for concurrent services; Python is popular for data scripts.\" Avoid saying one language is \"best\" without context."),
                EnglishItVocabulary.getVocabulary("programming-languages"),
                "Use comparative forms: \"Java is more verbose than Python for small scripts.\" \"Rust offers stricter memory safety than C.\"",
                List.of(
                        "\"We chose Java because the team already maintains Spring services.\"",
                        "\"The syntax is similar to C#, so onboarding is faster.\"",
                        "\"This script runs in the Node runtime.\""),
                List.of(
                        "Which language are you learning and why?",
                        "Compare two languages you know in three sentences."));

        lecture("networking",
                List.of(
                        "Describe how data moves across networks.",
                        "Explain routers, packets, and subnets.",
                        "Troubleshoot basic connectivity vocabulary."),
                List.of(
                        "Networks connect devices using switches, routers, and cables or wireless signals. A router forwards packets between networks, choosing the best path. Each packet carries a piece of data plus addressing information.",
                        "Subnets divide a large network into smaller segments for security and performance. DNS helps clients find servers by name instead of memorizing IP addresses. When latency is high, applications feel slow even if bandwidth is sufficient.",
                        "Engineers document topology diagrams and IP ranges. During incidents, clear updates matter: \"Traffic is failing between subnet A and the database VLAN after the firewall change.\""),
                EnglishItVocabulary.getVocabulary("networking"),
                "Use present continuous for ongoing issues: \"The router is dropping packets.\" Use present perfect for impact: \"We have migrated DNS to the new provider.\"",
                List.of(
                        "\"Please verify the subnet mask on the VM.\"",
                        "\"DNS propagation may take up to twenty-four hours.\"",
                        "\"We traced the packet loss to a misconfigured switch.\""),
                List.of(
                        "What is the role of a router vs. a switch?",
                        "Describe a network issue you have seen or heard about."));

        lecture("it-slang",
                List.of(
                        "Understand informal expressions in tech workplaces.",
                        "Use slang appropriately in chat and stand-ups.",
                        "Recognize when formal language is required."),
                List.of(
                        "IT teams use informal English in chat and daily stand-ups. \"Let's sync up\" means to meet briefly and align. \"Ping me\" means send a message. \"I don't have bandwidth\" often means lack of time, not network capacity.",
                        "A blocker is anything stopping progress. Teams ask: \"Any blockers?\" ASAP means as soon as possible — use it carefully and prefer clear deadlines in formal email. ASL in global teams sometimes means \"age, sex, location\" online, but in stand-ups context check your audience — when unsure, use plain English.",
                        "Slang builds rapport, but client emails and documentation should stay formal. Read the room: Slack with teammates vs. presentation to management require different registers."),
                EnglishItVocabulary.getVocabulary("it-slang"),
                "Register matters: informal \"Hey, quick ping when you're free\" vs. formal \"Please let me know when you are available to discuss the issue.\"",
                List.of(
                        "\"I'll ping you after the deployment.\"",
                        "\"This bug is my main blocker today.\"",
                        "\"Can we sync up for five minutes after lunch?\""),
                List.of(
                        "When is slang helpful, and when is it risky?",
                        "Rewrite an informal chat message as formal email."));

        lecture("cybersecurity",
                List.of(
                        "Articulate core security principles and threats.",
                        "Discuss authentication, encryption, and vulnerabilities.",
                        "Participate in incident conversations at C1 level."),
                List.of(
                        "Cybersecurity protects confidentiality, integrity, and availability — the CIA triad. Encryption transforms readable data into ciphertext so unauthorized parties cannot interpret it. Strong authentication verifies identity using passwords, tokens, or biometrics, often combined in multi-factor authentication.",
                        "Phishing attacks trick users into revealing credentials or installing malware. A vulnerability is a weakness that could be exploited; responsible teams patch known issues quickly. A breach occurs when attackers access data without authorization.",
                        "Security is everyone's responsibility. Developers must validate input, avoid hard-coded secrets, and follow secure coding guidelines. In incident calls, precise language reduces panic: \"We have contained the affected account and rotated keys.\""),
                EnglishItVocabulary.getVocabulary("cybersecurity"),
                "Nominalization is common at C1: \"The implementation of encryption policies reduces exposure.\" \"Authentication failure triggered the alert.\"",
                List.of(
                        "\"We enforce MFA for all production accounts.\"",
                        "\"The phishing email mimicked our IT help desk.\"",
                        "\"A critical vulnerability was disclosed in the logging library.\""),
                List.of(
                        "Explain MFA to a non-technical colleague.",
                        "What should you do if you suspect phishing?"));

        lecture("programmers",
                List.of(
                        "Communicate clearly in code reviews and documentation.",
                        "Use precise verbs for refactoring and debugging.",
                        "Describe legacy systems and merge conflicts professionally."),
                List.of(
                        "Professional programmers write code for humans as well as machines. In code reviews, be specific and respectful: \"Consider extracting this logic into a service class to improve testability\" is better than \"this is bad.\"",
                        "Refactoring improves structure without changing external behavior. When you hit a breakpoint during debugging, document steps to reproduce the issue. Legacy code is older software that is hard to change but still critical to the business.",
                        "Merge conflicts happen when Git cannot combine branches automatically. Resolve them carefully, run tests, and describe your changes in the pull request. Good PR descriptions save reviewers time and reduce release risk."),
                EnglishItVocabulary.getVocabulary("programmers"),
                "Use hedging in reviews: \"This might cause a race condition under load.\" \"I'd suggest renaming this method for clarity.\"",
                List.of(
                        "\"I refactored the payment module to reduce duplication.\"",
                        "\"There's a merge conflict in UserService.java.\"",
                        "\"Could you review the error handling in this PR?\""),
                List.of(
                        "What makes a helpful code review comment?",
                        "Describe how you would explain a bug to a teammate."));

        lecture("artificial-intelligence",
                List.of(
                        "Discuss machine learning concepts in professional English.",
                        "Address bias, datasets, and inference clearly.",
                        "Evaluate AI use cases in the workplace."),
                List.of(
                        "Artificial intelligence systems learn patterns from data. Machine learning models are trained on datasets and then used for inference — making predictions on new inputs. Neural networks are one approach among many; choice depends on data size, interpretability, and cost.",
                        "Bias in training data can produce unfair outcomes. Teams must document data sources, limitations, and evaluation metrics. Transparency builds trust with users and regulators.",
                        "In sprint planning, distinguish hype from value: \"This classifier automates triage of support tickets\" is concrete. \"We need AI everywhere\" is not. Senior engineers articulate trade-offs between accuracy, latency, and maintainability."),
                EnglishItVocabulary.getVocabulary("artificial-intelligence"),
                "Use nominal clauses for precision: \"What the model predicts depends on feature quality.\" \"Whether we deploy depends on validation results.\"",
                List.of(
                        "\"We retrained the model after cleaning the dataset.\"",
                        "\"Inference latency must stay under two hundred milliseconds.\"",
                        "\"We audit the system for bias before release.\""),
                List.of(
                        "Name one ethical risk of AI in education or hiring.",
                        "Explain inference vs. training in your own words."));

        lecture("qa",
                List.of(
                        "Write and discuss test cases and acceptance criteria.",
                        "Report defects with reproducible detail.",
                        "Explain regression and automation strategy."),
                List.of(
                        "Quality assurance ensures software meets requirements before release. A test case describes steps, expected results, and test data. Acceptance criteria define when a user story is complete from a business perspective.",
                        "When you find a defect, report severity, environment, and reproduction steps. Regression testing checks that new changes did not break existing features. Automation saves time for repetitive checks but requires maintenance.",
                        "QA engineers collaborate early in design to prevent costly bugs. In release meetings, clear status language helps: \"We have two critical defects blocking sign-off\" or \"Regression suite passed on staging.\""),
                EnglishItVocabulary.getVocabulary("qa"),
                "Passive and perfect aspects: \"The defect has been logged in Jira.\" \"The build was rejected due to failing tests.\"",
                List.of(
                        "\"I'll add a test case for the negative path.\"",
                        "\"Regression failed on the payment workflow.\"",
                        "\"Acceptance criteria are unclear — we need product input.\""),
                List.of(
                        "What belongs in a strong bug report?",
                        "When should teams prefer manual testing over automation?"));
    }

    private static void lecture(String unitId, List<String> objectives, List<String> paragraphs,
                                List<VocabEntry> vocabulary, String grammarTip,
                                List<String> practicePhrases, List<String> reflections) {
        LECTURES.put(unitId, new Lecture(unitId, objectives, paragraphs, vocabulary,
                grammarTip, practicePhrases, reflections));
    }

}
