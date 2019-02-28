[![Build Status](https://travis-ci.org/FelixsGit/IV1201-Project.svg?branch=master)](https://travis-ci.org/FelixsGit/IV1201-Project)

## RecruitmentSystem

### Description
RecruitmentSystem is a project developed for the KTH course Design of Global Application(IV1201). 
In short this program is a 'recruitment-system' used by a 'amusementpark company'. 
It is a full-stack application where the user interface is presented as a webpage. Here applicants can for example create accounts, login 
and apply for jobs. Likewise the 'company employees' can login and review these applications.

This project was a mandatory requirement in passing the course. For higer grades additional options and 
functionality could be added, for example secruity, testing and logging. 

### Installation
To install this project one can simply clone or download it by clicking the green box with the text 'clone or download'.
This project uses Spring with maven repositories and Travis CI for integration testing. Besides that the development 
was conducted with a local mariaDB database running MYSQL. Therefore it is important for forking or cloning users to change the affected 
variables to match their own environment-configuration. This is done in the Travis.yml and in the Application.properties files. 

### Usage
The webpage are straight forward to understand and could be understood by english and swedish reading users. The application are using
the framework thymeleaf for generating views. Another framework that are beeing used are JPA for the server database communication. Authentication and Authorization are handled by spring security, and logging are handled by Spring logger. To fully understand the application one needs basic knowledge in these framework. 
For simplification lets follow the register functionality throughout the system.

-->The user visits the url /register.
Here the user can fill out the form with the necessary information.
![register](https://user-images.githubusercontent.com/28272254/53576663-7fed1600-3b74-11e9-9846-65ec9039320c.PNG)

If the validaton is passed the user is sent to the application using this form. 

```Java
@PostMapping(DEFAULT_PAGE_URL + REGISTER_PAGE_URL)
    public String registerUser(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        LOGGER.trace("Post of registration data.");
        LOGGER.trace("Form data: " + registrationForm);
        if(bindingResult.hasErrors()) {
            return REGISTER_PAGE_URL;
        }
        try {
            applicationService.createPerson(registrationForm.getName(), registrationForm.getSurname(), registrationForm.getSsn(),
                    registrationForm.getEmail(), registrationForm.getPassword(), Role.APPLICANT, registrationForm.getUsername());
            model.addAttribute(new RegistrationForm());
        } catch (UserException exception) {
            registrationErrorHandling(exception, model);
            return REGISTER_PAGE_URL;
        }
        return showLoginView(model);
    }
```

-->The information filled in by the user gets saved in an RegistrationForm Object
Found here: https://github.com/FelixsGit/IV1201-Project/blob/master/src/main/java/se/kth/iv1201/recruitmentsystem/presentation/app/RegistrationForm.java

-->In the ApplicationController class there is a @PostMapping method that gets called when the user presses the button to register.
This method makes the appropriate call to the applicationService class in the service layer which then calls the repository needed to enter the information into the database. 



### Contributing

### Credits

### License

Description: A description of your project follows. A good description is clear, short, and to the point. Describe the importance of your project, and what it does.

Table of Contents: Optionally, include a table of contents in order to allow other people to quickly navigate especially long or detailed READMEs.

Installation: Installation is the next section in an effective README. Tell other users how to install your project locally. Optionally, include a gif to make the process even more clear for other people.

Usage: The next section is usage, in which you instruct other people on how to use your project after they’ve installed it. This would also be a good place to include screenshots of your project in action.

Contributing: Larger projects often have sections on contributing to their project, in which contribution instructions are outlined. Sometimes, this is a separate file. If you have specific contribution preferences, explain them so that other developers know how to best contribute to your work. To learn more about how to help others contribute, check out the guide for setting guidelines for repository contributors.

Credits: Include a section for credits in order to highlight and link to the authors of your project.

License: Finally, include a section for the license of your project. For more information on choosing a license, check out GitHub’s licensing guide!
