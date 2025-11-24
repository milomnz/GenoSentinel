import {Entity, PrimaryGeneratedColumn, Column, ManyToOne, JoinColumn, ManyToMany} from 'typeorm';
import { Patient } from "../../patient/entity/patient.entity";
import { TumorType } from "src/modules/tumorType/entities/TumorType.entity";

@Entity('clinical_record')
export class ClinicalRecord{

    @PrimaryGeneratedColumn('increment')
    id: number;

    //Relacion con Patient 
    @ManyToOne(() => Patient, (patient) =>patient.clinicalRecords, {nullable: false})
    @JoinColumn({name: 'id_Patient'})
    idPatient:Patient;

    //Relacion con TumorType
    @ManyToMany(() => TumorType, (tumorType)=> tumorType.clinicalRecords)
    
    tumorTypes: TumorType[];

    @Column({type:'date', name: 'diagnostic_date'})
    diagnosticDate: Date;

    @Column ({length: 50})
    stage:string;
    @Column({name: 'treatmentProtocol', length: 230})
    treatmentProtocol: string;   
}