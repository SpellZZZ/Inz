package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class BindedDriversDto implements Serializable {


        private static final long serialVersionUID = 5926468583005150707L;
        private int user_id;
        private int truck_id;
        private int trailer_id;
        private String userName;
        private String userSurname;
        private String truckModel;
        private String truckReg;
        private String trailerDesc;
        private String toString;



        public BindedDriversDto() {
        }

        public BindedDriversDto(int user_id, int truck_id, int trailer_id, String userName, String userSurname, String truckModel, String truckReg, String trailerDesc, String toString) {
                this.user_id = user_id;
                this.truck_id = truck_id;
                this.trailer_id = trailer_id;
                this.userName = userName;
                this.userSurname = userSurname;
                this.truckModel = truckModel;
                this.truckReg = truckReg;
                this.trailerDesc = trailerDesc;
                this.toString = toString;
        }
}
