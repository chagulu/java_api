package com.example.society.dto;

// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;

public class ResidenceRegisterRequest {
    private String password;
    private String mobileNo;
    private String email;

    private String residenceName;
    private String address;
    private String city;
    private String state;
    // REMOVED: private String pincode; // <--- REMOVE THIS LINE

    private String buildingNumber;
    private String flatNumber;

    private Integer totalMembers;
    private String vehicleDetails;


    public ResidenceRegisterRequest() { }

    // --- UPDATED CONSTRUCTOR ---
    public ResidenceRegisterRequest(
            String password,
            String mobileNo,
            String email,
            String residenceName,
            String address,
            String city,
            String state,
            // REMOVED: String pincode, // <--- REMOVE THIS PARAMETER
            String buildingNumber,
            String flatNumber,
            Integer totalMembers,
            String vehicleDetails
    ) {
        this.password = password;
        this.mobileNo = mobileNo;
        this.email = email;
        this.residenceName = residenceName;
        this.address = address;
        this.city = city;
        this.state = state;
        // REMOVED: this.pincode = pincode; // <--- REMOVE THIS ASSIGNMENT
        this.buildingNumber = buildingNumber;
        this.flatNumber = flatNumber;
        this.totalMembers = totalMembers;
        this.vehicleDetails = vehicleDetails;
    }

    // Getters and Setters for existing fields (unchanged)
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getResidenceName() { return residenceName; }
    public void setResidenceName(String residenceName) { this.residenceName = residenceName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    // REMOVED: getPincode() and setPincode() methods // <--- REMOVE THESE METHODS
    /*
    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    */

    public String getBuildingNumber() { return buildingNumber; }
    public void setBuildingNumber(String buildingNumber) { this.buildingNumber = buildingNumber; }

    public String getFlatNumber() { return flatNumber; }
    public void setFlatNumber(String flatNumber) { this.flatNumber = flatNumber; }

    public Integer getTotalMembers() { return totalMembers; }
    public void setTotalMembers(Integer totalMembers) { this.totalMembers = totalMembers; }

    public String getVehicleDetails() { return vehicleDetails; }
    public void setVehicleDetails(String vehicleDetails) { this.vehicleDetails = vehicleDetails; }
}