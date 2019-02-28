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
The webpage are straight forward to understand and could be understood by english and swedish reading users. 

The application are using the framework thymeleaf for generating views. Another framework that are beeing used are JPA for the server database communication. Authentication and Authorization are handled by spring security, and logging are handled by Spring logger. To fully understand the application one needs basic knowledge in these framework. 
For simplification lets follow the register functionality throughout the system.

-->The user visits the url /register.
Here the user can fill out the form with the necessary information.
![register](https://user-images.githubusercontent.com/28272254/53576663-7fed1600-3b74-11e9-9846-65ec9039320c.PNG)

-->The information filled in by the user gets saved in an RegistrationForm Object
Found here: 

https://github.com/FelixsGit/IV1201-Project/blob/master/src/main/java/se/kth/iv1201/recruitmentsystem/presentation/app/RegistrationForm.java

-->The following method, seen below in the ApplicationController class will then be invoked.  

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
-->This method makes the appropriate call to the ApplicationService class in the service layer which then calls the repository needed to enter the information into the database seen here:

```Java
public PersonDTO createPerson(String name, String surname, String ssn, String email,
                                  String password, String roleName, String username) throws UserException {
        Role role = roleRepository.findRoleByName(roleName);
        if(role == null)
            throw new UserException("Role name " + roleName + " does not exist in database.");

        if(personRepository.findPersonByUsername(username) != null)
            throw new UserException("Username " + username + " is already taken.");

        if(personRepository.findPersonByEmail(email) != null)
            throw new UserException("Email " + email + " is already in use.");

        if(personRepository.findPersonBySsn(ssn) != null)
            throw new UserException("SSN " + ssn + " is already registered.");

        Person person = new Person(name, surname, ssn, email, password, role, username);
        try {
            personRepository.save(person);
        } catch (Exception exception) {
            throw new UserException("Something went wrong when saving profile in database");
        }
        return person;
    }
```
By doing it like this one could easily add on more functionality in a quick and easy way.

### Contributing
We perfer a dm if you are willing to contribute to this project.

### Credits
The developers listed below all took part in this project.

Adrian Zander 
https://github.com/Acander

Joey Öhman 
https://github.com/JoeyOhman

Felix Toppar
https://github.com/FelixsGit

### License
MIT License

Copyright (c) [year] [fullname]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


