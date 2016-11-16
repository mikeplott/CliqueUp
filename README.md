#Simple Taskrunner

Lightweight scaffolding and build tool to quickly and easily integrate a React+Backbone build with another backend.

  - transpiles scss into css
  - transpiles es6 and jsx into es5
  - concatenates all required/imported modules together into minified files in a `public/` directory

##Getting started

  - **starting inside the root of the project-directory** clone this repo with `git clone git@github.com:TIY-Charleston-Front-End-Engineering/simple-taskrunner.git .`
  - disconnect from my remote repo and point to your own (see [Publishing](#publishing))
  - `npm install`
  - `npm run go`
  - that's it! visit *http://localhost:«your-port-setting»*

##Workflow
  - only files in `/public` will be served up to the browser.
  - all `.html` pages are served from the `/views` folder
  - all assets (CSS, JS, and images) are served from the `/public` folder
  - you'll *only* write CSS and JS code in the files in the `/src` folder
  - whenever you save while your local server is running (`npm run go`), your code in the `/src-client` folder will be read and transpiled into the `/public` folder.
  - view and test your app at localhost:3000

##Publishing
  - add, commit and push per usual

  
