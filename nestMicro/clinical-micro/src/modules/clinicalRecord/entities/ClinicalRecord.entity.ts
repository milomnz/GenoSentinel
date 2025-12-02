import { Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn } from 'typeorm';
import { Patient } from "../../patient/entity/patient.entity";
import { TumorType } from "src/modules/tumorType/entities/TumorType.entity";

@Entity('clinicalrecord')
export class ClinicalRecord {

    @PrimaryGeneratedColumn('increment')
    id: number;

    // Relación con Patient
    @ManyToOne(() => Patient, (patient) => patient.clinicalRecords, { nullable: false })
    @JoinColumn({ name: 'idPatient' })
    patient: Patient;

    @ManyToOne(() => TumorType)
    @JoinColumn({ name: 'idTumorType' })
    tumorType: TumorType;

    @Column({ type: 'date', name: 'diagnosticDate' })
    diagnosticDate: Date;

    @Column({ length: 50 })
    stage: string;

    @Column({ name: 'treatmentProtocol', length: 230 })
    treatmentProtocol: string;
}