import { Patient } from "src/modules/patient/entity/patient.entity";
import { TumorType } from "src/modules/tumorType/domain/entities/TumorType.entity";

export class ClinicalRecord{
    constructor(
        public readonly id: bigint,
        public idpatient: bigint,
        public idtumorType: bigint,
        public diagnosticDate: Date,
        public stage: string,
        public treatmentProtocol: string,
        
    ){}
}