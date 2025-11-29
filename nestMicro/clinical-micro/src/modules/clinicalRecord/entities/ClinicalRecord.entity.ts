import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from 'typeorm';
import { Patient } from "../../patient/entity/patient.entity";
import { TumorType } from "src/modules/tumorType/entities/TumorType.entity";

@Entity('clinical_record')
export class ClinicalRecord {

    @PrimaryGeneratedColumn('increment')
    id: number;

    // Relación con Patient
    @ManyToOne(() => Patient, (patient) => patient.clinicalRecords, { nullable: false })
    @JoinColumn({ name: 'id_patient' })
    patient: Patient;

    @ManyToOne(() => TumorType) 
    @JoinColumn({ name: 'id_tumor_type' })
    tumorType: TumorType;

    @Column({ type: 'date', name: 'diagnostic_date' })
    diagnosticDate: Date;

    @Column({ length: 50 })
    stage: string;

    @Column({ name: 'treatment_protocol', length: 230 })
    treatmentProtocol: string;
}