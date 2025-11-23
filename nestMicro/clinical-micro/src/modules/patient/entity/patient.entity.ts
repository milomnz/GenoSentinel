import { Entity, PrimaryGeneratedColumn, Column } from 'typeorm';

@Entity()
export class Patient {
    @PrimaryGeneratedColumn()
    id?: number;
    @Column({ length: 100 })
    name: string;
    @Column({ length: 100 })
    lastName: string;
    @Column()
    birthDate: Date;
    @Column()
    gender: string;
    @Column()
    status: string;
}