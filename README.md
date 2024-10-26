# Professional Courier App

The **Professional Courier App** is a Kotlin-based Android application designed to streamline the courier data entry and PDF generation process for delivery partners. This app eliminates the manual workflow of warehouse data entry, enabling delivery partners to manage courier details, generate PDFs, and attach them to shipments independently. It simplifies the overall process and increases productivity.

## Key Features

1. **User Authentication**: Secure login for delivery partners using assigned usernames and passwords.
2. **Automated Courier Details Management**:
   - Auto-fetches client and branch details.
   - Provides one-click email functionality to send all branch-related emails simultaneously.
3. **Location-based Credit Booking**:
   - Fetches the current location of the delivery partner using latitude and longitude.
4. **Dynamic Data Entry**:
   - Auto-populates fields like destination based on entered pin codes.
   - Calculates and auto-fills dimensions (length, width, height) based on item quantity.
5. **PDF Management**:
   - Allows viewing and downloading of generated PDFs.
   - Stores PDFs in Room database, with download options for external storage only when needed.
6. **Database and API Integration**:
   - Uses Room database to manage PDF data storage.
   - Communicates with a Spring Boot backend through REST APIs for secure and efficient data management.
   
## Screens and Workflow

![First collection](https://drive.google.com/uc?export=view&id=1CzBfnT7tvGjhuWMxjfIv3oF3IqY7YfgT)
![Second collection](https://drive.google.com/uc?export=view&id=1Deg8Xc_gFP41ADckSIdDnu80y0jpO6fe)

1. **Login Screen**: 
   - Partners log in with a username and password assigned to them.
   
2. **Home Screen**:
   - Displays client details (branch name, code).
   - Button to send all emails related to the branch.
   - Option for credit booking, which navigates to the next screen after fetching the user’s current location.

3. **Credit Booking Screen**:
   - Fields to input booking details.
   - Auto-populates the destination list based on the pin code entered.

4. **Dimensions Screen**:
   - Calculates and auto-fills length, width, and height based on item quantity.
   
5. **Invoice Screen**:
   - Allows users to enter all necessary invoice-related fields.

6. **PDF Management Screen**:
   - Displays generated PDFs with options to view or download each PDF.

## Technology Stack

### Client-side (Android)

- **Language**: Kotlin with Jetpack Compose for a modern UI experience.
- **Architecture**: MVVM (Model-View-ViewModel) for separation of concerns.
- **Libraries**:
  - **OkHttpClient**: For secure and efficient network calls.
  - **FusedLocationProviderClient**: To get the current location of the user.
  - **SharedPreferences**: For secure local storage of login credentials, keeping users logged in unless they log out.
  - **Room Database**: To store and manage PDF data within the app, avoiding excessive use of external storage.
  - **Component-based Architecture**: Enables reusable components throughout the app.
  - **NavHost**: For seamless navigation between screens.

### Server-side (Spring Boot)

- **Language**: Java, using Spring Boot.
- **Architecture**: MVC (Model-View-Controller) for better separation of concerns.
- **APIs**:
  - `POST /firmDetails` - Retrieves firm details.
  - `POST /findBranchByBranchCode` - Retrieves branch information by branch code.
  - `POST /findEmailByBranch` - Fetches email addresses related to a branch.
  - `POST /findEmailByBranchCode` - Retrieves email addresses by branch code.
  - `POST /getAddressDetails` - Gets address information.
  - `POST /getConsignmentNumber` - Fetches consignment numbers.
  - `POST /submitCreditBooking` - Submits credit booking details.
  - `GET /getDestinationsByPinCode` - Retrieves destinations based on a pin code.
  - `POST /sendEmails` - Sends emails related to a branch.
  - `POST /login` - Authenticates the user.

### Usage

1. Open the app and log in using provided credentials.
2. Enter details for each courier, navigate between screens, and complete data entry as prompted.
3. Generate a PDF for each courier with the option to view or download.
4. Send emails related to the branch in bulk via the home screen.

## Project Structure

### Client (Android)

- **MainActivity**: Hosts the `NavHost` for screen navigation.
- **ViewModels**: Manages all the UI-related logic and state.
- **UI**: Contains all UI components and screens built using Jetpack Compose.
- **Data**: 
  - **Configs**: Holds configuration files for the app.
  - **Room Database**: Manages local PDF data storage.
  - **Repositories**: Handles data operations, including API calls using `OkHttpClient`.
  - **Utils**: Includes utility classes, such as the PDF generator.

### Server (Spring Boot)

- **Controller Layer**: Handles incoming HTTP requests and routes them to the service layer.
- **Service Layer**: Processes business logic and interacts with repositories.
- **Repository Layer**: Manages database interactions.
- **Model**: Contains database entity classes representing tables.
- **DTO (Data Transfer Object)**: Defines data transfer objects used for communication between different layers of the application.
