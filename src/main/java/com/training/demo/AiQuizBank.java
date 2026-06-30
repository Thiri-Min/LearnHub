package com.training.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class AiQuizBank {

    private AiQuizBank() {
    }

    public static List<Map<String, Object>> preIntermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What does AI stand for?", List.of("Artificial Intelligence", "Automated Integration", "Applied Informatics", "Algorithmic Inference"), 0));
        list.add(QuizQuestion.of("Which best describes artificial intelligence?", List.of("Systems that perform tasks requiring human-like intelligence", "Only robots with physical bodies", "Spreadsheet automation macros", "Static rule-based calculators"), 0));
        list.add(QuizQuestion.of("Machine learning is primarily a subset of which field?", List.of("Artificial Intelligence", "Computer networking", "Relational databases", "Operating system kernels"), 0));
        list.add(QuizQuestion.of("What is the main goal of supervised learning?", List.of("Learn a mapping from inputs to known labels", "Find hidden structure without labels", "Generate hardware designs", "Optimize network routing tables"), 0));
        list.add(QuizQuestion.of("In supervised learning, each training example typically includes what?", List.of("Features and a known target label", "Only unlabeled feature vectors", "Only output labels without inputs", "Random binary files"), 0));
        list.add(QuizQuestion.of("Which task is a classic supervised learning problem?", List.of("Email spam classification", "Customer segmentation without labels", "Topic discovery in unlabeled text", "Anomaly detection with no labels"), 0));
        list.add(QuizQuestion.of("Which task is an example of unsupervised learning?", List.of("Grouping customers by purchase behavior without labels", "Predicting house prices from labeled data", "Classifying images with known categories", "Translating text with parallel corpora"), 0));
        list.add(QuizQuestion.of("What is a dataset in machine learning?", List.of("A collection of examples used for training or evaluation", "A single trained model file", "A GPU driver configuration", "A network firewall rule"), 0));
        list.add(QuizQuestion.of("What is a feature in ML?", List.of("An individual measurable property of data", "The final prediction only", "The loss function source code", "A deployment container image"), 0));
        list.add(QuizQuestion.of("What is a label in supervised learning?", List.of("The correct output you want the model to predict", "A UI button on a dashboard", "A version tag in Git", "A CSS class name"), 0));
        list.add(QuizQuestion.of("What does NLP stand for?", List.of("Natural Language Processing", "Neural Logic Programming", "Network Layer Protocol", "Numeric Linear Processing"), 0));
        list.add(QuizQuestion.of("Which is an NLP task?", List.of("Sentiment analysis of product reviews", "Sorting integers in memory", "Rendering HTML in a browser", "Compiling Java bytecode"), 0));
        list.add(QuizQuestion.of("What does LLM stand for?", List.of("Large Language Model", "Low Latency Memory", "Linear Learning Method", "Local Link Manager"), 0));
        list.add(QuizQuestion.of("LLMs are primarily trained to do what?", List.of("Predict the next token in text sequences", "Store relational table indexes", "Route IP packets on a network", "Compile C++ to machine code"), 0));
        list.add(QuizQuestion.of("Training in ML refers to what?", List.of("Adjusting model parameters using data", "Deploying a model to production only", "Writing unit tests for APIs", "Creating DNS records"), 0));
        list.add(QuizQuestion.of("Inference in ML refers to what?", List.of("Using a trained model to make predictions on new data", "Collecting raw training data only", "Designing database schemas", "Writing Git commit messages"), 0));
        list.add(QuizQuestion.of("Why separate training from inference?", List.of("Training is compute-heavy; inference serves live predictions", "They are identical processes", "Inference always retrains the model", "Training only happens after deployment"), 0));
        list.add(QuizQuestion.of("Which Python library is commonly used for numerical arrays?", List.of("NumPy", "Flask", "Requests", "BeautifulSoup"), 0));
        list.add(QuizQuestion.of("Which library provides DataFrame structures for tabular data?", List.of("pandas", "NumPy only", "Matplotlib only", "Pillow"), 0));
        list.add(QuizQuestion.of("Which library offers common ML algorithms like decision trees?", List.of("scikit-learn", "Django", "FastAPI", "Selenium"), 0));
        list.add(QuizQuestion.of("In pandas, what is a DataFrame?", List.of("A two-dimensional labeled data structure", "A neural network layer", "A Git branch", "An HTTP response code"), 0));
        list.add(QuizQuestion.of("What is overfitting?", List.of("Model memorizes training data and performs poorly on new data", "Model is too simple to learn patterns", "Dataset has no features", "Training uses no labels"), 0));
        list.add(QuizQuestion.of("What is underfitting?", List.of("Model is too simple to capture patterns in data", "Model memorizes every training example", "Dataset is perfectly balanced", "Model has too many parameters only on test data"), 0));
        list.add(QuizQuestion.of("What is a training set used for?", List.of("Fitting model parameters", "Final unbiased performance estimate only", "Storing production logs", "Defining HTTP routes"), 0));
        list.add(QuizQuestion.of("What is a test set used for?", List.of("Estimating performance on unseen data", "Tuning model weights during training", "Replacing all training data", "Storing source code backups"), 0));
        list.add(QuizQuestion.of("What is classification?", List.of("Predicting a discrete category label", "Predicting a continuous numeric value", "Clustering without labels", "Compressing image files"), 0));
        list.add(QuizQuestion.of("What is regression?", List.of("Predicting a continuous numeric value", "Assigning emails to spam or not spam", "Grouping documents by topic", "Translating languages only"), 0));
        list.add(QuizQuestion.of("Which metric suits binary classification evaluation?", List.of("Accuracy or F1 score", "Mean squared error only", "BLEU score only", "Lines of code count"), 0));
        list.add(QuizQuestion.of("What is bias in an ML model context?", List.of("Systematic error from overly simple assumptions", "Random noise in labels only", "GPU memory size", "HTTP caching behavior"), 0));
        list.add(QuizQuestion.of("What is variance in an ML model context?", List.of("Sensitivity to fluctuations in the training set", "Always equals zero in good models", "Number of database tables", "CSS specificity score"), 0));
        list.add(QuizQuestion.of("What is ethical AI concerned with?", List.of("Fairness, transparency, and responsible use", "Maximizing model size only", "Removing all human oversight", "Ignoring privacy regulations"), 0));
        list.add(QuizQuestion.of("Why can biased training data be harmful?", List.of("Models can amplify unfair patterns in decisions", "It always improves accuracy", "It reduces storage needs", "It eliminates need for testing"), 0));
        list.add(QuizQuestion.of("What is data privacy in AI?", List.of("Protecting personal information in datasets and outputs", "Publishing all user data publicly", "Disabling encryption", "Sharing API keys in repos"), 0));
        list.add(QuizQuestion.of("What is a prompt in the context of LLMs?", List.of("Input text instructing the model what to generate", "A shell command alias", "A SQL primary key", "A Docker volume name"), 0));
        list.add(QuizQuestion.of("What is tokenization in NLP?", List.of("Splitting text into units the model processes", "Encrypting model weights", "Training a CNN on images", "Sorting a binary tree"), 0));
        list.add(QuizQuestion.of("What is a corpus?", List.of("A large collection of text used for NLP tasks", "A single labeled image", "A network subnet", "A Java interface"), 0));
        list.add(QuizQuestion.of("What is supervised learning also called?", List.of("Learning with labeled data", "Learning without any data", "Unsupervised clustering", "Reinforcement from rewards only"), 0));
        list.add(QuizQuestion.of("Which is unsupervised?", List.of("K-means clustering", "Linear regression with labels", "Logistic regression", "Naive Bayes classification"), 0));
        list.add(QuizQuestion.of("What does sklearn commonly refer to?", List.of("scikit-learn machine learning library", "A SQL dialect", "A React hook", "A Kubernetes controller"), 0));
        list.add(QuizQuestion.of("NumPy arrays are especially useful because they support what?", List.of("Fast vectorized numerical operations", "HTML templating", "OAuth token exchange", "Git merge conflict resolution"), 0));
        list.add(QuizQuestion.of("What is a feature vector?", List.of("Numeric representation of an input example", "A list of Git branches", "An HTTP header collection", "A CSS selector chain"), 0));
        list.add(QuizQuestion.of("What is ground truth?", List.of("The true label or value for an example", "A model hyperparameter", "A random seed", "A deployment region"), 0));
        list.add(QuizQuestion.of("Why clean datasets before training?", List.of("Noise and errors can mislead the model", "Cleaning always reduces accuracy", "Models ignore bad rows automatically", "Cleaning replaces feature engineering"), 0));
        list.add(QuizQuestion.of("What is a common AI application in chatbots?", List.of("Understanding and generating natural language", "Defragmenting hard drives", "Compiling kernels", "Managing RAID arrays"), 0));
        list.add(QuizQuestion.of("What is transfer learning at a high level?", List.of("Reusing a pretrained model for a related task", "Copying datasets without permission", "Moving files between disks", "Switching Git remotes"), 0));
        list.add(QuizQuestion.of("What is an epoch in training?", List.of("One full pass through the training dataset", "A single gradient step only", "Model deployment to cloud", "A REST API endpoint"), 0));
        list.add(QuizQuestion.of("What is a batch in training?", List.of("A subset of examples processed together", "The entire internet", "A production load balancer", "A database migration script"), 0));
        list.add(QuizQuestion.of("What is the purpose of a validation set?", List.of("Tune hyperparameters without leaking test performance", "Replace the test set entirely", "Store passwords securely", "Serve static web assets"), 0));
        list.add(QuizQuestion.of("Which describes inference latency?", List.of("Time to produce a prediction for one request", "Time to collect training data", "Number of Git commits", "Size of a CSS file"), 0));
        list.add(QuizQuestion.of("Why document AI system limitations?", List.of("Users should understand failure modes and boundaries", "Documentation slows inference", "Regulators forbid transparency", "It prevents model updates"), 0));
        return list;
    }

    public static List<Map<String, Object>> intermediate() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What is a neural network?", List.of("Layers of connected units that learn representations", "A physical brain implant", "A SQL join algorithm", "A DNS resolver"), 0));
        list.add(QuizQuestion.of("What is an activation function used for?", List.of("Introducing non-linearity into the network", "Sorting training batches alphabetically", "Encrypting model weights", "Parsing JSON payloads"), 0));
        list.add(QuizQuestion.of("What does CNN stand for?", List.of("Convolutional Neural Network", "Central Neural Node", "Cascading Network Normalizer", "Compiled Numeric Notation"), 0));
        list.add(QuizQuestion.of("CNNs are especially suited for what?", List.of("Image and spatial pattern recognition", "Plain CSV regression only", "Git version control", "HTTP caching"), 0));
        list.add(QuizQuestion.of("What does RNN stand for?", List.of("Recurrent Neural Network", "Random Neural Node", "Rapid Numeric Normalization", "Remote Network Namespace"), 0));
        list.add(QuizQuestion.of("RNNs are designed to handle what?", List.of("Sequential data with temporal dependencies", "Static tabular data only", "DNS lookups", "CSS layout only"), 0));
        list.add(QuizQuestion.of("What is gradient descent?", List.of("An optimization method that updates parameters along loss gradients", "A data cleaning technique", "A network routing protocol", "A Git branching strategy"), 0));
        list.add(QuizQuestion.of("What is a loss function?", List.of("A measure of how wrong predictions are", "A database index type", "A UI color palette", "An OAuth scope"), 0));
        list.add(QuizQuestion.of("Why compute gradients during training?", List.of("To know how to adjust weights to reduce loss", "To encrypt training data", "To render HTML faster", "To merge Git branches"), 0));
        list.add(QuizQuestion.of("What is precision in classification?", List.of("True positives divided by predicted positives", "True positives divided by actual positives", "Accuracy on training set only", "Total examples in dataset"), 0));
        list.add(QuizQuestion.of("What is recall in classification?", List.of("True positives divided by actual positives", "True positives divided by predicted positives", "False positives divided by negatives", "Loss after one epoch"), 0));
        list.add(QuizQuestion.of("What is an F1 score?", List.of("Harmonic mean of precision and recall", "Sum of precision and recall", "Difference of accuracy and loss", "Learning rate times batch size"), 0));
        list.add(QuizQuestion.of("What is a hyperparameter?", List.of("A setting chosen before training such as learning rate", "A weight updated by gradient descent", "A label in the dataset", "An HTTP status code"), 0));
        list.add(QuizQuestion.of("What does regularization help prevent?", List.of("Overfitting by penalizing model complexity", "Underfitting by removing all features", "Data collection", "Model deployment"), 0));
        list.add(QuizQuestion.of("What is L2 regularization also known as?", List.of("Weight decay", "Dropout removal", "Batch normalization only", "Early stopping only"), 0));
        list.add(QuizQuestion.of("What is dropout?", List.of("Randomly disabling units during training to improve generalization", "Deleting the entire dataset", "Removing the loss function", "Stopping all gradients permanently"), 0));
        list.add(QuizQuestion.of("What is early stopping?", List.of("Halting training when validation performance stops improving", "Training forever without limits", "Removing the test set", "Skipping validation entirely"), 0));
        list.add(QuizQuestion.of("What is a transformer architecture known for?", List.of("Self-attention mechanisms processing sequences in parallel", "Only convolution on images", "Manual feature engineering only", "Sorting arrays in O(n)"), 0));
        list.add(QuizQuestion.of("What is self-attention?", List.of("Mechanism where tokens weigh relevance of other tokens", "A database foreign key", "A CSS flex property", "A Git rebase operation"), 0));
        list.add(QuizQuestion.of("What is an embedding?", List.of("Dense vector representation capturing semantic meaning", "A plaintext password", "A Docker image tag", "An XML schema"), 0));
        list.add(QuizQuestion.of("Word embeddings map words to what?", List.of("Continuous vector space where similar words are close", "Random integers only", "SQL table names", "HTTP ports"), 0));
        list.add(QuizQuestion.of("What does RAG stand for?", List.of("Retrieval-Augmented Generation", "Random Access Gradient", "Recursive API Gateway", "Rapid Auto Grouping"), 0));
        list.add(QuizQuestion.of("How does RAG improve LLM responses?", List.of("Retrieves relevant documents to ground generation", "Deletes the prompt before answering", "Disables the tokenizer", "Trains from scratch each query"), 0));
        list.add(QuizQuestion.of("What is prompt engineering?", List.of("Designing inputs to elicit reliable model behavior", "Writing GPU drivers", "Configuring Kubernetes ingress only", "Designing SQL indexes only"), 0));
        list.add(QuizQuestion.of("What is a learning rate?", List.of("Step size for parameter updates in optimization", "Number of layers in a network", "Dataset row count", "API rate limit"), 0));
        list.add(QuizQuestion.of("What is backpropagation?", List.of("Algorithm to compute gradients through the network", "Forward-only inference pass", "Data augmentation technique", "Model serving protocol"), 0));
        list.add(QuizQuestion.of("What is a fully connected layer?", List.of("Each neuron connects to all neurons in the previous layer", "Only neighboring pixels connect", "No connections exist", "Layers share no weights"), 0));
        list.add(QuizQuestion.of("What is batch normalization used for?", List.of("Stabilizing and speeding training by normalizing layer inputs", "Encrypting labels", "Parsing HTML templates", "Managing Git hooks"), 0));
        list.add(QuizQuestion.of("What is cross-entropy loss common for?", List.of("Multi-class classification problems", "Image resizing", "Database migrations", "DNS configuration"), 0));
        list.add(QuizQuestion.of("What is mean squared error common for?", List.of("Regression problems", "Multi-label text classification only", "Clustering evaluation only", "Tokenization"), 0));
        list.add(QuizQuestion.of("What is a confusion matrix?", List.of("Table showing predicted vs actual class counts", "A network topology diagram", "A Git conflict list", "A Docker compose file"), 0));
        list.add(QuizQuestion.of("What is a false positive?", List.of("Incorrectly predicted positive", "Correctly predicted positive", "Correctly predicted negative", "Missing label in dataset"), 0));
        list.add(QuizQuestion.of("What is a false negative?", List.of("Incorrectly predicted negative", "Correctly predicted positive", "Correctly predicted negative", "Hyperparameter tuned too high"), 0));
        list.add(QuizQuestion.of("Why tune hyperparameters on a validation set?", List.of("Avoid overfitting choices to the test set", "Replace training data", "Skip inference", "Remove regularization"), 0));
        list.add(QuizQuestion.of("What is an epoch?", List.of("One complete pass through the training data", "A single mini-batch update only", "Model export to ONNX", "API gateway deployment"), 0));
        list.add(QuizQuestion.of("What is a mini-batch?", List.of("Small subset of data used for one update step", "Entire internet crawl", "Production traffic only", "Validation set alone"), 0));
        list.add(QuizQuestion.of("What is vanishing gradient problem?", List.of("Gradients become too small in deep networks hindering learning", "Loss becomes negative always", "Learning rate is too high only", "Dataset is too small only"), 0));
        list.add(QuizQuestion.of("What is ReLU activation?", List.of("Returns max(0, x) introducing sparsity and non-linearity", "Always outputs zero", "Sorts input features", "Computes softmax only"), 0));
        list.add(QuizQuestion.of("What is softmax used for?", List.of("Converting logits to a probability distribution", "Image convolution", "Token byte encoding", "SQL aggregation"), 0));
        list.add(QuizQuestion.of("What is fine-tuning at intermediate level?", List.of("Adapting a pretrained model on task-specific data", "Training from random weights only", "Deleting pretrained weights", "Disabling gradients"), 0));
        list.add(QuizQuestion.of("What is a tokenizer in LLMs?", List.of("Converts text to model input token IDs", "Encrypts model checkpoints", "Schedules Kubernetes pods", "Validates JWT signatures"), 0));
        list.add(QuizQuestion.of("What is temperature in LLM sampling?", List.of("Controls randomness of generated tokens", "GPU thermal limit", "Database connection pool size", "CSS animation duration"), 0));
        list.add(QuizQuestion.of("What is few-shot prompting?", List.of("Providing examples in the prompt to guide behavior", "Training with zero examples ever", "Removing all context", "Disabling attention"), 0));
        list.add(QuizQuestion.of("What is zero-shot prompting?", List.of("Asking the model without explicit in-prompt examples", "Providing hundreds of labeled examples in weights", "Fine-tuning on every query", "Using only random tokens"), 0));
        list.add(QuizQuestion.of("What is chain-of-thought prompting?", List.of("Encouraging step-by-step reasoning in the output", "Hiding intermediate reasoning always", "Disabling multi-token generation", "Using binary labels only"), 0));
        list.add(QuizQuestion.of("What is an attention head?", List.of("A parallel attention computation subspace", "A HTTP request header", "A database shard key", "A Git submodule"), 0));
        list.add(QuizQuestion.of("Why use embeddings in search systems?", List.of("Similar meaning yields similar vectors for retrieval", "Embeddings replace all databases", "They compress JPEG images", "They compile Java code"), 0));
        list.add(QuizQuestion.of("What is semantic search?", List.of("Finding items by meaning rather than exact keywords", "Sorting strings alphabetically only", "Hash table lookup by key only", "Ping-based network discovery"), 0));
        list.add(QuizQuestion.of("What is model calibration?", List.of("Aligning predicted probabilities with observed frequencies", "Increasing parameter count only", "Removing validation data", "Disabling loss computation"), 0));
        list.add(QuizQuestion.of("What is a learning rate scheduler?", List.of("Adjusts learning rate during training for better convergence", "Deletes training checkpoints", "Replaces the loss function", "Disables batch normalization"), 0));
        return list;
    }

    public static List<Map<String, Object>> advanced() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(QuizQuestion.of("What is deep learning?", List.of("ML using many-layered neural networks", "Spreadsheet formulas only", "Manual if-else rules only", "DNS load balancing"), 0));
        list.add(QuizQuestion.of("What is reinforcement learning?", List.of("Learning via rewards and penalties from environment interaction", "Supervised learning with fixed labels only", "Unsupervised clustering only", "Static SQL queries"), 0));
        list.add(QuizQuestion.of("What is an agent in RL?", List.of("Entity that observes state and chooses actions", "A CI pipeline runner", "A database connection pool", "A CSS preprocessor"), 0));
        list.add(QuizQuestion.of("What is a policy in RL?", List.of("Mapping from states to actions", "A privacy regulation", "A Git ignore file", "An HTTP cache header"), 0));
        list.add(QuizQuestion.of("What is MLOps?", List.of("Practices for deploying and maintaining ML in production", "Only training in notebooks", "Hardware manufacturing", "Frontend bundling only"), 0));
        list.add(QuizQuestion.of("What does a model registry support in MLOps?", List.of("Versioning and tracking trained models", "Storing passwords in plain text", "Replacing source control", "Disabling monitoring"), 0));
        list.add(QuizQuestion.of("What is a GAN?", List.of("Generative model with generator and discriminator networks", "Gradient aggregation norm", "General API node", "Git automation network"), 0));
        list.add(QuizQuestion.of("In a GAN, what does the discriminator do?", List.of("Distinguish real data from generated samples", "Generate images only", "Compute SQL queries", "Route HTTP traffic"), 0));
        list.add(QuizQuestion.of("What is a diffusion model?", List.of("Generative model that learns to denoise data iteratively", "A database replication method", "A load balancer algorithm", "A Git merge strategy"), 0));
        list.add(QuizQuestion.of("What is LoRA in fine-tuning?", List.of("Low-Rank Adaptation adding small trainable matrices", "Large Object Retrieval API", "Linear Optimization Runtime Agent", "Local Only Random Access"), 0));
        list.add(QuizQuestion.of("Why use LoRA for fine-tuning?", List.of("Train fewer parameters efficiently on limited hardware", "Delete the base model weights", "Disable all gradients", "Replace tokenizer entirely"), 0));
        list.add(QuizQuestion.of("What is RLHF?", List.of("Reinforcement Learning from Human Feedback", "Random Layer Hash Function", "Remote Logging HTTP Framework", "Recursive Lambda Hosting Fabric"), 0));
        list.add(QuizQuestion.of("RLHF is commonly used to do what?", List.of("Align LLM behavior with human preferences", "Compress PNG images", "Index relational tables", "Parse HTML forms"), 0));
        list.add(QuizQuestion.of("What is federated learning?", List.of("Training across decentralized data without centralizing raw data", "Training only on a single laptop", "Deleting all client data", "Using only public benchmarks"), 0));
        list.add(QuizQuestion.of("What is differential privacy?", List.of("Adding noise to protect individual data contributions", "Publishing raw user records", "Removing all model accuracy", "Disabling encryption"), 0));
        list.add(QuizQuestion.of("What is an adversarial example?", List.of("Input crafted to fool a model while appearing normal", "A correctly labeled training row", "A validated unit test", "A signed JWT token"), 0));
        list.add(QuizQuestion.of("What is AI alignment?", List.of("Ensuring systems pursue intended human goals safely", "Maximizing parameter count", "Removing safety evaluations", "Hiding model behavior"), 0));
        list.add(QuizQuestion.of("What is XAI?", List.of("Explainable AI methods that clarify model decisions", "Exclusive API Integration", "Extended Authentication Interface", "External Asset Indexing"), 0));
        list.add(QuizQuestion.of("What is SHAP used for in XAI?", List.of("Explaining feature contributions to predictions", "Scheduling Kubernetes jobs", "Parsing OAuth responses", "Compiling Thymeleaf"), 0));
        list.add(QuizQuestion.of("What is LIME in XAI?", List.of("Local interpretable model-agnostic explanations", "A loss function for GANs", "A language tokenizer", "A load testing tool"), 0));
        list.add(QuizQuestion.of("What is a reward function in RL?", List.of("Defines numeric feedback for actions taken", "A CSS animation keyframe", "A database migration version", "An API gateway plugin"), 0));
        list.add(QuizQuestion.of("What is Q-learning?", List.of("RL algorithm learning action values for states", "SQL query optimization", "Quick sort implementation", "Queue-based logging"), 0));
        list.add(QuizQuestion.of("What is experience replay in deep RL?", List.of("Storing transitions to sample diverse training batches", "Replaying production traffic only", "Re-running Git history", "Caching HTTP responses only"), 0));
        list.add(QuizQuestion.of("What is a deployment canary in MLOps?", List.of("Gradual rollout to a subset of traffic", "Deleting old models instantly", "Training without monitoring", "Disabling versioning"), 0));
        list.add(QuizQuestion.of("What is data drift?", List.of("Change in input data distribution over time", "Constant identical data forever", "GPU temperature increase", "Git branch divergence only"), 0));
        list.add(QuizQuestion.of("What is model drift?", List.of("Degrading performance as real-world data shifts", "Improving accuracy without retraining", "Increasing batch size only", "Static weights forever"), 0));
        list.add(QuizQuestion.of("What is A/B testing for models?", List.of("Comparing model variants on live traffic segments", "Training without evaluation", "Removing logging", "Single global deployment only"), 0));
        list.add(QuizQuestion.of("What is feature store in MLOps?", List.of("Central repository for reusable curated features", "A UI theme store", "A password vault", "A CDN edge cache"), 0));
        list.add(QuizQuestion.of("What is ONNX used for?", List.of("Interoperable model exchange between frameworks", "OAuth token signing", "HTML templating", "Git LFS storage"), 0));
        list.add(QuizQuestion.of("What is model quantization?", List.of("Reducing numeric precision to speed inference", "Increasing model size arbitrarily", "Deleting embeddings", "Removing activation functions"), 0));
        list.add(QuizQuestion.of("What is knowledge distillation?", List.of("Training smaller model to mimic larger teacher model", "Copying datasets illegally", "Merging Git branches", "Sharding SQL tables"), 0));
        list.add(QuizQuestion.of("What is a VAE?", List.of("Variational Autoencoder for probabilistic latent representations", "Virtual API Endpoint", "Validated Authentication Extension", "Vectorized Array Engine"), 0));
        list.add(QuizQuestion.of("What is mode collapse in GAN training?", List.of("Generator produces limited variety of outputs", "Discriminator always wins instantly", "Loss becomes exactly zero always", "Dataset doubles in size"), 0));
        list.add(QuizQuestion.of("What is classifier-free guidance in diffusion?", List.of("Technique balancing conditional and unconditional scores", "Removing all conditioning", "Training without noise", "Using only GAN loss"), 0));
        list.add(QuizQuestion.of("What is PPO in RL?", List.of("Proximal Policy Optimization algorithm", "Public Policy Object", "Parallel Processing Orchestrator", "Persistent Pod Operator"), 0));
        list.add(QuizQuestion.of("What is safe RL concerned with?", List.of("Constraints on actions to avoid harmful outcomes", "Maximizing reward without limits", "Removing environment simulation", "Ignoring human oversight"), 0));
        list.add(QuizQuestion.of("What is homomorphic encryption relevance to FL?", List.of("Compute on encrypted data reducing exposure", "Store plaintext everywhere", "Disable client participation", "Replace gradients with images"), 0));
        list.add(QuizQuestion.of("What is secure aggregation in federated learning?", List.of("Combining updates without revealing individual client values", "Publishing each client model publicly", "Training on centralized raw data", "Skipping encryption"), 0));
        list.add(QuizQuestion.of("What is membership inference attack?", List.of("Inferring whether a sample was in training data", "Guessing HTTP ports", "Breaking CSS layout", "Reversing Git hashes easily"), 0));
        list.add(QuizQuestion.of("What is model inversion attack?", List.of("Reconstructing training data from model outputs", "Inverting a binary tree", "Reversing DNS records", "Flipping UI colors"), 0));
        list.add(QuizQuestion.of("What is constitutional AI?", List.of("Training models with explicit principle-based rules", "Removing all safety guidelines", "Using only unlabeled data", "Disabling human review"), 0));
        list.add(QuizQuestion.of("What is red teaming for AI?", List.of("Adversarial testing to find harmful behaviors", "Deploying without tests", "Removing audit logs", "Disabling monitoring alerts"), 0));
        list.add(QuizQuestion.of("What is observability for ML services?", List.of("Metrics, logs, and traces on predictions and latency", "Hiding all inference errors", "Removing dashboards", "Training without validation"), 0));
        list.add(QuizQuestion.of("What is shadow deployment?", List.of("Running new model parallel without serving user traffic", "Deleting production model first", "Training on production passwords", "Disabling load balancers"), 0));
        list.add(QuizQuestion.of("What is continual learning challenge?", List.of("Learning new tasks without catastrophic forgetting", "Training once forever without updates", "Removing old data always", "Using only random labels"), 0));
        list.add(QuizQuestion.of("What is catastrophic forgetting?", List.of("New training erases performance on old tasks", "Model remembers everything perfectly", "Dataset becomes immutable", "Loss cannot decrease"), 0));
        list.add(QuizQuestion.of("What is an attribution map in CNN XAI?", List.of("Highlights image regions influencing the prediction", "Shows Git commit authors", "Lists SQL indexes", "Maps DNS zones"), 0));
        list.add(QuizQuestion.of("What is counterfactual explanation?", List.of("Shows minimal input change to alter prediction", "Repeats training loss curve", "Lists all hyperparameters", "Exports raw weights only"), 0));
        list.add(QuizQuestion.of("What is AI governance?", List.of("Policies and controls for responsible AI lifecycle", "Unrestricted model deployment", "Removing documentation", "Sharing private keys"), 0));
        list.add(QuizQuestion.of("What is human-in-the-loop in ML?", List.of("Humans review or correct model outputs in workflow", "Fully autonomous decisions always", "Removing all audits", "Disabling feedback channels"), 0));
        return list;
    }

}
