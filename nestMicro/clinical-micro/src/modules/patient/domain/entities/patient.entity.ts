export class Patient {
    constructor(
        public readonly id: bigint,
        public name: string,
        public lastName: string,
        public birthDate: Date,
        public gender: string,
        public status: string,
    ) {}
}