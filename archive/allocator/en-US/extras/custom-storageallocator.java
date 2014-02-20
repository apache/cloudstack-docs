      /**
        * Determines which storage pools are suitable for the guest virtual machine
        * @param DiskProfile dskCh
        * @param VirtualMachineProfile vmProfile
        * @param DeploymentPlan plan
        * @param ExcludeList avoid
        * @param int returnUpTo
        * @return List<StoragePool> List of storage pools that are suitable for the VM
      **/

      public List<StoragePool> allocateToPool(DiskProfile dskCh, VirtualMachineProfile<? extends VirtualMachine> vm, DeploymentPlan plan, ExcludeList avoid, int returnUpTo);
