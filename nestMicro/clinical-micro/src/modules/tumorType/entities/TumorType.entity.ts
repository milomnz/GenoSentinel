import {Entity, PrimaryGeneratedColumn, Column, OneToMany, ManyToMany, JoinTable} from 'typeorm';
import { ClinicalRecord } from 'src/modules/clinicalRecord/entities/ClinicalRecord.entity';

@Entity('tumor_type')
export class TumorType{
    @PrimaryGeneratedColumn('increment')
    id: number;

    @Column({length: 50, unique: true })
    name:string;

    @Column({name: 'systemAffected', length:70 })
    systemAffected: string;

    @ManyToMany (()=> ClinicalRecord,(record) => record.tumorTypes)

    @JoinTable({
        name: 'tumor_type_clinical_record',
        joinColumn: {name: 'id_tumor_type', referencedColumnName: 'id'},
        inverseJoinColumn: {name: 'id_clinical_record', referencedColumnName: 'id'},
    })

    clinicalRecords: ClinicalRecord[];
}